package io.codelex.flightplanner.api;

import io.codelex.flightplanner.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private FlightInMemoryService service;

    public CustomerController(FlightInMemoryService service) {
        this.service = service;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return service.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlight(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {
        return service.searchFlights(searchFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight findFlightById(@PathVariable int id) {
        return service.fetchFlight(id);
    }
}
