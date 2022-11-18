package io.codelex.flightplanner.admin_api;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminInMemoryRepository {

    List<Flight> flightList = new ArrayList<>();
    private long nextId;

    public AdminInMemoryRepository() {
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

    long getNextId() {
        return this.nextId;
    }

//    public void resetLastUsedId() {
//        this.nextId = 0;
//    }
}
