package io.codelex.flightplanner.api.common;

import io.codelex.flightplanner.domain.AddFlightResponse;
import io.codelex.flightplanner.domain.Flight;

import java.time.format.DateTimeFormatter;

public class CommonFunctions {

    public static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public static AddFlightResponse createNewResponseFlightObject(Flight flight) {
        return new AddFlightResponse(
                flight.getId(),
                flight.getFrom(),
                flight.getTo(),
                flight.getCarrier(),

                getFormatter().format(flight.getDepartureTime()),
                getFormatter().format((flight.getArrivalTime()))
        );
    }
}
