package io.codelex.flightplanner.api;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class FlightInMemoryRepository {

    List<Flight> flightList = new ArrayList<>();
    private volatile AtomicInteger nextId;
    public FlightInMemoryRepository() {
        nextId = new AtomicInteger(0);
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    Flight addFlight(Flight flight) {
        getFlightList().add(flight);
        this.nextId.incrementAndGet();
        return flight;
    }

    AtomicInteger getNextId() {
        return this.nextId;
    }


    public void clearData() {
        getFlightList().clear();
    }
}
