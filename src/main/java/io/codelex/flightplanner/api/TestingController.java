package io.codelex.flightplanner.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {

    private final FlightInMemoryService service;

    public TestingController(FlightInMemoryService service) {
        this.service = service;
    }
    @PostMapping("/clear")
    public void clear() {
        service.clearData();
    }
}
