package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Ticket {
    private String token;
    private Seat seat;

    public Ticket(String token, Seat ticket) {
        this.token = token;
        this.seat = ticket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("ticket")
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
