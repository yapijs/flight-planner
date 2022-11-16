package io.codelex.flightplanner;

import io.codelex.flightplanner.domain.AddFlightRequest;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminInMemoryRepository {

    List<Flight> flightList = new ArrayList<>();

    public AdminInMemoryRepository() {
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public Flight addFlight(Flight flight) {
        getFlightList().add(flight);
        return getFlightList().get(getFlightList().size() - 1);
    }

    protected long getNextId() {
        return getFlightList().size();
    }

    public void deleteAll() {
    }
}
