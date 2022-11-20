package io.codelex.flightplanner.api.common;

import io.codelex.flightplanner.domain.Flight;

import java.time.format.DateTimeFormatter;

public class CommonFunctions {

    public static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
}
