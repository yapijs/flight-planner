package io.codelex.flightplanner;

import io.codelex.flightplanner.admin_api.AdminInMemoryRepository;
import io.codelex.flightplanner.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private AdminInMemoryRepository adminInMemoryRepository;

    public CustomerService(AdminInMemoryRepository adminInMemoryRepository) {
        this.adminInMemoryRepository = adminInMemoryRepository;
    }

    public List<Airport> searchAirports(String search) {
        List<Airport> list = new ArrayList<>();
        for (Flight flight : adminInMemoryRepository.getFlightList()) {
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

        for (Flight flight : adminInMemoryRepository.getFlightList()) {
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

    public AddFlightResponse findFlightById(long id) {
        for(Flight flight: adminInMemoryRepository.getFlightList() ) {
            if (id == flight.getId()) {
                return createNewResponseFlightObject(flight);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private AddFlightResponse createNewResponseFlightObject(Flight flight) {
        return new AddFlightResponse(
                flight.getId(),
                flight.getFrom(),
                flight.getTo(),
                flight.getCarrier(),
                getFormatter().format(flight.getDepartureTime()),
                getFormatter().format((flight.getArrivalTime()))
        );
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
}
