package io.codelex.flightplanner.api;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightInMemoryRepository {

    List<Flight> flightList = new ArrayList<>();
    private int nextId;

    public FlightInMemoryRepository() {
        nextId = 0;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    Flight addFlight(Flight flight) {
        getFlightList().add(flight);
        this.nextId++;
        return flight;
    }

    int getNextId() {
        return this.nextId;
    }

    public void resetLastUsedId() {
        this.nextId = 0;
    }
}
