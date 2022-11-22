package io.codelex.flightplanner.api.common;

import java.time.format.DateTimeFormatter;

public class CommonFunctions {

    public static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
}
