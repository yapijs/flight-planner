package io.codelex.flightplanner.domain;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Objects;

public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String formatAirport() {
        return this.getAirport().toUpperCase();
    }

    public String formatCity() {
        return formatFirstLetters(this.city);
    }

    public String formatCountry() {
        return formatFirstLetters(this.country);
    }

    private String formatFirstLetters(String text) {
        String[] arrayOfWords = text.split(" ");
        for (String string : arrayOfWords) {
            string = string.substring(0, 1).toUpperCase() + string.substring(1);
        }
        return Arrays.stream(arrayOfWords).reduce("", (s, s2) ->  s + " " + s2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equals(airport1.country) && city.equals(airport1.city) && airport.equals(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
