package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    int ROWS = 9;
    int COLUMNS = 9;
    Cinema cinema = new Cinema(ROWS, COLUMNS);


    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }


    @PostMapping("/purchase")
    public ResponseEntity<?> buySeat(@RequestBody Seat purchase) {
        int seatIndex = (purchase.getRow() - 1) * COLUMNS + purchase.getColumn() - 1;
        if (purchase.getRow() > ROWS || purchase.getRow() < 0
                || purchase.getColumn() > COLUMNS || purchase.getColumn() < 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("The number of a row or a column is out of bounds!"));
        } else if (cinema.getAvailableSeats().get(seatIndex).isTaken()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("The ticket has been already purchased!"));
        } else {
            Ticket ticket = cinema.purchaseTicket(seatIndex);
            return ResponseEntity.ok(ticket);
        }
    }


    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Ticket ticket) {
        if (cinema.getPurchasedTickets().containsKey(ticket.getToken())) {
            Seat returnedTicket = cinema.returnTicket(ticket.getToken());
            return ResponseEntity.ok(new ReturnedTicketResponse(returnedTicket));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Wrong token!"));
        }
    }


    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {
        if ("super_secret".equals(password)) {
            return ResponseEntity.ok(cinema.getStats());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("The password is wrong!"));
        }
    }
}
