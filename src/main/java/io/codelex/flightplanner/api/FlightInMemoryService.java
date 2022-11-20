package io.codelex.flightplanner.api;

import io.codelex.flightplanner.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.codelex.flightplanner.api.common.CommonFunctions.getFormatter;

@Service
public class FlightInMemoryService {

    private FlightInMemoryRepository flightInMemoryRepository;

    public FlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        this.flightInMemoryRepository = flightInMemoryRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
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
        return addFlightRequest.getFrom().formatAirport().equals(addFlightRequest.getTo().formatAirport())
                && addFlightRequest.getFrom().formatCity().equals(addFlightRequest.getTo().formatCity())
                && addFlightRequest.getFrom().formatCountry().equals(addFlightRequest.getTo().formatCountry());
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
        Flight flight = findFlight(flightId);
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return flight;
    }

    private Flight findFlight(int flightId) {
        return flightInMemoryRepository.getFlightList()
                .stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst()
                .orElse(null);
    }

    public void deleteFlight(int flightId) {
        Flight flightToDelete = findFlight(flightId);
        if (flightToDelete != null) {
            flightInMemoryRepository.getFlightList().remove(flightToDelete);
        }
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
        List<Flight> matchingFlights = new ArrayList<>();

        for (Flight flight : flightInMemoryRepository.getFlightList()) {
            if (compareSearchAndFlight(searchFlightRequest, flight)) {
                matchingFlights.add(flight);
            }
        }

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
        flightInMemoryRepository.getFlightList().clear();
        flightInMemoryRepository.resetLastUsedId();
    }
}
