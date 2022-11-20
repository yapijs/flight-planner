package io.codelex.flightplanner.domain;

import java.util.Objects;

public class AddFlightResponse extends AddFlightRequest{

    private int id;

    public AddFlightResponse(int id, Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        super(from, to, carrier, departureTime, arrivalTime);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AddFlightResponse that = (AddFlightResponse) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "AddFlightResponse{" +
                "id=" + id +
                '}';
    }
}
