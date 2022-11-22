package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Flight {
    private AtomicInteger id;
    private Airport from;
    private Airport to;
    private String carrier;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    public Flight(AtomicInteger id, Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public AtomicInteger getId() {
        return id;
    }

    public void setId(AtomicInteger id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return from.equals(flight.from)
                && to.equals(flight.to)
                && carrier.equals(flight.carrier)
                && departureTime.equals(flight.departureTime)
                && arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, carrier, departureTime, arrivalTime);
    }
}
