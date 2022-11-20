package io.codelex.flightplanner.testing;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {

    private TestingInMemoryService testingInMemoryService;

    public TestingController(TestingInMemoryService testingInMemoryService) {
        this.testingInMemoryService = testingInMemoryService;
    }

    @PostMapping("/clear")
    public void clear() {
        testingInMemoryService.clearData();
    }

}
