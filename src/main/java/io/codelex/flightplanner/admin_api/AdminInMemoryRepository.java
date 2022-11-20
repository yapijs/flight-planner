package io.codelex.flightplanner.admin_api;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AdminInMemoryRepository {

    List<Flight> flightList = new ArrayList<>();
    private AtomicInteger nextId;

    public AdminInMemoryRepository() {
        nextId = new AtomicInteger(0);
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    Flight addFlight(Flight flight) {
        getFlightList().add(flight);
        //this.nextId++;
        this.nextId.getAndIncrement();
        return flight;
    }

    AtomicInteger getNextId() {
        return this.nextId;
    }



    public void resetLastUsedId() {
        this.nextId.set(0);
    }
}
