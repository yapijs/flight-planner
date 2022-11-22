package io.codelex.flightplanner.api;

import io.codelex.flightplanner.domain.*;
import io.codelex.flightplanner.dto.AddFlightRequest;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static io.codelex.flightplanner.api.common.CommonFunctions.getFormatter;

@Service
public class FlightInMemoryService {

    private final FlightInMemoryRepository flightInMemoryRepository;

    public FlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        this.flightInMemoryRepository = flightInMemoryRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
        addFlightRequest.getFrom().formatAirportObject();
        addFlightRequest.getTo().formatAirportObject();

        validateAddingFlightRequest(addFlightRequest);

        Flight flight = createNewFlightObject(addFlightRequest);
        if (flightInMemoryRepository.getFlightList().contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return flightInMemoryRepository.addFlight(flight);
    }

    private void validateAddingFlightRequest(AddFlightRequest addFlightRequest) {
        if (validateDatesAddFlightRequest(addFlightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect dates!");
        }
        if (validateAirportsAddFlightRequest(addFlightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same airports entered!");
        }
    }

    private boolean validateAirportsAddFlightRequest(AddFlightRequest addFlightRequest) {
        return addFlightRequest.getFrom().getAirport().equals(addFlightRequest.getTo().getAirport())
                && addFlightRequest.getFrom().getCity().equals(addFlightRequest.getTo().getCity())
                && addFlightRequest.getFrom().getCountry().equals(addFlightRequest.getTo().getCountry());
    }

    private boolean validateDatesAddFlightRequest(AddFlightRequest addFlightRequest) {
        LocalDateTime dateTimeDeparture = LocalDateTime.parse(addFlightRequest.getDepartureTime(), getFormatter());
        LocalDateTime dateTimeArrival = LocalDateTime.parse(addFlightRequest.getArrivalTime(), getFormatter());
        return dateTimeDeparture.compareTo(dateTimeArrival) >= 0;
    }

    private Flight createNewFlightObject(AddFlightRequest addFlightRequest) {
        return new Flight(
                flightInMemoryRepository.getNextId(),
                addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), getFormatter()),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), getFormatter())
        );
    }

    public Flight fetchFlight(int flightId) {
        return findFlight(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    public void deleteFlight(int flightId) {
        findFlight(flightId)
                .ifPresent(flight -> flightInMemoryRepository
                        .getFlightList()
                        .remove(flight));
    }

    private Optional<Flight> findFlight(int flightId) {
        return flightInMemoryRepository.getFlightList()
                .stream()
                .filter(flight -> flight.getId().get() == flightId)
                .findFirst();
    }

    public List<Airport> searchAirports(String search) {
        List<Airport> list = new ArrayList<>();
        for (Flight flight : flightInMemoryRepository.getFlightList()) {
            if (compareAirport(flight.getFrom(), search)) {
                list.add(flight.getFrom());
            }
            if (compareAirport(flight.getTo(), search)) {
                list.add(flight.getTo());
            }
        }
        return list;
    }

    private boolean compareAirport(Airport airport, String search) {
        String searchPhrase = search.toLowerCase().trim();
        return airport.getAirport().toLowerCase().contains(searchPhrase) ||
                airport.getCountry().toLowerCase().contains(searchPhrase) ||
                airport.getCity().toLowerCase().contains(searchPhrase);
    }

    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        validateSearchFlightRequest(searchFlightRequest);
        List<Flight> matchingFlights = flightInMemoryRepository.getFlightList()
                .stream()
                .filter(flight -> compareSearchAndFlight(searchFlightRequest, flight))
                .toList();

        return new PageResult<>(0, matchingFlights.size(), matchingFlights);
    }

    private boolean compareSearchAndFlight(SearchFlightRequest search, Flight flight) {
        return flight.getFrom().getAirport().equals(search.getFrom())
                && flight.getTo().getAirport().equals(search.getTo())
                && flight.getDepartureTime().toLocalDate().isEqual(search.getDepartureDate());
    }

    private void validateSearchFlightRequest(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void clearData() {
        flightInMemoryRepository.clearData();
    }
}
