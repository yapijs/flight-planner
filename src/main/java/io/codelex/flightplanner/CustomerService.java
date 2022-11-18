package io.codelex.flightplanner;

import io.codelex.flightplanner.admin_api.AdminInMemoryRepository;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private AdminInMemoryRepository adminInMemoryRepository;

    public CustomerService(AdminInMemoryRepository adminInMemoryRepository) {
        this.adminInMemoryRepository = adminInMemoryRepository;
    }

    public List<Airport> searchAirports(String search) {
        List<Airport> list = new ArrayList<>();
        for (Flight flight : adminInMemoryRepository.getFlightList()) {
            if (compareAirport(flight.getFrom(), search)) {
                list.add(flight.getFrom());
            }
            ;
            if (compareAirport(flight.getTo(), search)) {
                list.add(flight.getTo());
            }
            ;
        }
        return list;
    }

    private boolean compareAirport(Airport airport, String search) {
        String searchPhrase = search.toLowerCase().trim();
        return airport.getAirport().toLowerCase().contains(searchPhrase) ||
                airport.getCountry().toLowerCase().contains(searchPhrase) ||
                airport.getCity().toLowerCase().contains(searchPhrase);
    }
}
