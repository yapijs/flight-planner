package io.codelex.flightplanner.testing;

import io.codelex.flightplanner.api.admin.AdminInMemoryRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingInMemoryService {

    AdminInMemoryRepository adminInMemoryRepository;

    public TestingInMemoryService(AdminInMemoryRepository adminInMemoryRepository) {
        this.adminInMemoryRepository = adminInMemoryRepository;
    }

    public void clearData() {
        adminInMemoryRepository.getFlightList().clear();
        adminInMemoryRepository.resetLastUsedId();
    }
}
