package io.codelex.flightplanner.testing_api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingApiController {

    private TestingApiService testingApiService;

    public TestingApiController(TestingApiService testingApiService) {
        this.testingApiService = testingApiService;
    }

    @PostMapping("testing-api/clear")
    public void clear() {
        testingApiService.clearData();
    }

}
