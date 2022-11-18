package io.codelex.flightplanner.admin_api;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminInMemoryRepository {

    List<Flight> flightList = new ArrayList<>();
    private long lastUsedId;

    public AdminInMemoryRepository() {
        lastUsedId = 0;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    Flight addFlight(Flight flight) {
        getFlightList().add(flight);
        this.lastUsedId++;
        //return getFlightList().get(getFlightList().size() - 1);
        return flight;
    }

    long getNextId() {
        return this.lastUsedId;
    }


}
