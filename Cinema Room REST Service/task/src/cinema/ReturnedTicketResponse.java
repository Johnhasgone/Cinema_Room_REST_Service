package cinema;

public class ReturnedTicketResponse {
    private Seat returnedTicket;

    public ReturnedTicketResponse(Seat returnedSeat) {
        this.returnedTicket = returnedSeat;
    }

    public Seat getReturnedTicket() {
        return returnedTicket;
    }

    public void setReturnedTicket(Seat returnedTicket) {
        this.returnedTicket = returnedTicket;
    }
}
