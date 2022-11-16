package io.codelex.flightplanner.testing_api;

import io.codelex.flightplanner.AdminInMemoryRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingInMemoryService {

    AdminInMemoryRepository adminInMemoryRepository;

    public TestingInMemoryService(AdminInMemoryRepository adminInMemoryRepository) {
        this.adminInMemoryRepository = adminInMemoryRepository;
    }

    public void clearData() {
        adminInMemoryRepository.getFlightList().clear();
    }
}
