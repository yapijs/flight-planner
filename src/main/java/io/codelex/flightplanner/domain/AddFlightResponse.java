package io.codelex.flightplanner.domain;

public class AddFlightResponse extends AddFlightRequest{

    private long id;

    public AddFlightResponse(long id, Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        super(from, to, carrier, departureTime, arrivalTime);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
