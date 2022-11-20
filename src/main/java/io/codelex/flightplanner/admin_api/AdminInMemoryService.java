package io.codelex.flightplanner.admin_api;

import io.codelex.flightplanner.domain.AddFlightRequest;
import io.codelex.flightplanner.domain.AddFlightResponse;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AdminInMemoryService {

    private AdminInMemoryRepository adminInMemoryRepository;

    public AdminInMemoryService(AdminInMemoryRepository adminInMemoryRepository) {
        this.adminInMemoryRepository = adminInMemoryRepository;
    }


    public AddFlightResponse addFlight(AddFlightRequest addFlightRequest) {
        validateAddingFlightRequest(addFlightRequest);

        Flight flight = createNewFlightObject(addFlightRequest);
        if (adminInMemoryRepository.getFlightList().contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Flight addedFlight = adminInMemoryRepository.addFlight(flight);
        return createNewResponseFlightObject(addedFlight);
    }

    private void validateAddingFlightRequest(AddFlightRequest addFlightRequest) {
        if (validateDatesAddFlightRequest(addFlightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect dates!");
//            System.out.println("incorrect dates=\n" + addFlightRequest.toString());
//            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Incorrect dates!");
        }
        if (validateAirportsAddFlightRequest(addFlightRequest)) {
//            System.out.println("same airports=\n" + addFlightRequest.toString());
//            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Same airports entered!");
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
                adminInMemoryRepository.getNextId().intValue(),
                addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), getFormatter()),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), getFormatter())
        );
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

    public AddFlightResponse fetchFlight(int flightId) {
        Flight flight = findFlight(flightId);
        if (flight != null) {
            return createNewResponseFlightObject(findFlight(flightId));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private Flight findFlight(int flightId) {
        return adminInMemoryRepository.getFlightList()
                .stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst()
                .orElse(null);
    }

    public void deleteFlight(int flightId) {
        Flight flightToDelete = findFlight(flightId);
        if (flightToDelete != null) {
            adminInMemoryRepository.getFlightList().remove(flightToDelete);
        }
    }
}
