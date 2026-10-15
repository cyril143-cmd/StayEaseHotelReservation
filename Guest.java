// GUEST CLASS

class Guest extends User {
    private String guestId;
    private String contactNumber;
    private static int nextId = 1;

    public Guest(String name, String contactNumber) {
        super(String.format("G%03d", nextId), name);

        this.guestId = String.format("G%03d", nextId);
        this.contactNumber = contactNumber;

        nextId++;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // MAKE RESERVATION
    public Reservation makeReservation(
            Receptionist receptionist,
            Room room,
            String checkInDate,
            String checkOutDate) {

        return receptionist.createReservation(
            this, room, checkInDate, checkOutDate
        );
    }

    // CANCEL RESERVATION
    public boolean cancelReservation(
            Receptionist receptionist,
            String reservationId) {

        Reservation reservation =
            receptionist.findReservation(reservationId);

        if (reservation == null
                || !reservation.getGuest().getGuestId()
                    .equals(this.guestId)) {
            return false;
        }

        return receptionist.cancelReservation(reservationId);
    }

    @Override
    public String getRole() {
        return "Guest";
    }
}
