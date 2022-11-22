package io.codelex.flightplanner.api;

import io.codelex.flightplanner.dto.AddFlightRequest;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private final FlightInMemoryService service;

    public AdminController(FlightInMemoryService service) {
        this.service = service;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return service.addFlight(addFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight findFlight(@PathVariable("id") int id) {
        return service.fetchFlight(id);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") int id) {
        service.deleteFlight(id);
    }

}
