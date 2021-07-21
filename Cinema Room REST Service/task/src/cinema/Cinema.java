package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;


public class Cinema {
    private int totalRows;
    private int totalColumns;
    private List<Seat> availableSeats;
    private Map<String, Seat> purchasedTickets;
    private Stats stats;

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;

        this.availableSeats = new ArrayList<>();
        this.purchasedTickets = new HashMap<>();
        this.stats = new Stats();

        initCinemaParameters();
    }

    private void initCinemaParameters() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                availableSeats.add(new Seat(i, j));
            }
        }

        this.stats.setCurrentIncome(0);
        this.stats.setNumberOfAvailableSeats(this.totalRows * this.totalColumns);
        this.stats.setNumberOfPurchasedTickets(this.purchasedTickets.size());
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    @JsonIgnore
    public Map<String, Seat> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(Map<String, Seat> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    @JsonIgnore
    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Ticket purchaseTicket(int seatIndex) {
        Seat purchasedSeat = this.getAvailableSeats().get(seatIndex);
        purchasedSeat.setTaken(true);
        String token = UUID.randomUUID().toString();
        this.getPurchasedTickets().put(token, purchasedSeat);
        this.getStats().setCurrentIncome(this.getStats().getCurrentIncome() + purchasedSeat.getPrice());
        this.getStats().setNumberOfPurchasedTickets(this.getStats().getNumberOfPurchasedTickets() + 1);
        this.getStats().setNumberOfAvailableSeats(this.getStats().getNumberOfAvailableSeats() - 1);

        return new Ticket(token, purchasedSeat);
    }

    public Seat returnTicket(String token) {
        Seat returnedTicket = this.getPurchasedTickets().remove(token);
        int seatIndex = (returnedTicket.getRow() - 1) * this.getTotalColumns() + returnedTicket.getColumn() - 1;
        this.getAvailableSeats().get(seatIndex).setTaken(false);
        this.getStats().setCurrentIncome(this.getStats().getCurrentIncome() - returnedTicket.getPrice());
        this.getStats().setNumberOfAvailableSeats(this.getStats().getNumberOfAvailableSeats() + 1);
        this.getStats().setNumberOfPurchasedTickets(this.getStats().getNumberOfPurchasedTickets() - 1);
        return returnedTicket;
    }
}



