package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.AddFlightRequest;
import io.codelex.flightplanner.domain.AddFlightResponse;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AdminInMemoryService {

    private AdminInMemoryRepository adminInMemoryRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AdminInMemoryService(AdminInMemoryRepository adminInMemoryRepository) {
        this.adminInMemoryRepository = adminInMemoryRepository;
    }


//    public Flight addFlight(AddFlightRequest addFlightRequest) {
//        return adminInMemoryRepository.addFlight(addFlightRequest);
//    }

    public AddFlightResponse addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(
                adminInMemoryRepository.getNextId(),
                addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                dateTimeFormatter(addFlightRequest.getDepartureTime()),
                dateTimeFormatter(addFlightRequest.getArrivalTime()));
        Flight addedFlight = adminInMemoryRepository.addFlight(flight);
        AddFlightResponse addFlightResponse = new AddFlightResponse(
                addedFlight.getId(),
                addedFlight.getFrom(),
                addedFlight.getTo(),
                addedFlight.getCarrier(),
                formatter.format(addedFlight.getDepartureTime()),
                formatter.format((addedFlight.getArrivalTime()))
        );
        return addFlightResponse;
    }

    private LocalDateTime dateTimeFormatter(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
