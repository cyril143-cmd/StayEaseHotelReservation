// RESERVATION CLASS

class Reservation {
    private String reservationId;
    private Guest guest;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private String status;

    // CONSTRUCTOR
    public Reservation(
            String reservationId,
            Guest guest,
            Room room,
            String checkInDate,
            String checkOutDate) {

        this.reservationId = reservationId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = "Confirmed";
    }

    // GETTERS
    public String getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    // CREATE RESERVATION
    public void createReservation() {
        status = "Confirmed";
        room.updateAvailability(false);
    }

    // CANCEL RESERVATION
    public void cancelReservation() {
        if (status.equals("Confirmed")) {
            status = "Cancelled";
            room.updateAvailability(true);
        }
    }

    // UPDATE RESERVATION STATUS
    public void updateStatus(String status) {
        this.status = status;

        if (status.equalsIgnoreCase("Cancelled")) {
            room.updateAvailability(true);
        } else if (status.equalsIgnoreCase("Confirmed")) {
            room.updateAvailability(false);
        }
    }

    // DISPLAY RESERVATION INFORMATION
    @Override
    public String toString() {
        return reservationId
                + " | Guest: " + guest.getName()
                + " | Room: " + room.getRoomNumber()
                + " | Check-in: " + checkInDate
                + " | Check-out: " + checkOutDate
                + " | Status: " + status;
    }
}
