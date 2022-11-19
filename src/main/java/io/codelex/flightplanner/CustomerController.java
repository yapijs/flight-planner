package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return customerService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlight(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {
        return customerService.searchFlights(searchFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public AddFlightResponse findFlightById(@PathVariable long id) {
        return customerService.findFlightById(id);
    }
}
