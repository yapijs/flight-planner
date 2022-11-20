package io.codelex.flightplanner.api.admin;

import io.codelex.flightplanner.domain.AddFlightRequest;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private AdminInMemoryService adminService;

    public AdminController(AdminInMemoryService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return adminService.addFlight(addFlightRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable("id") int id) {
        return adminService.fetchFlight(id);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") int id) {
        adminService.deleteFlight(id);
    }

}
