import java.util.ArrayList;

// RECEPTIONIST CLASS

class Receptionist extends User {
    private String employeeId;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private int nextReservationNumber = 1;

    // CONSTRUCTOR
    public Receptionist(String employeeId, String name) {
        super(employeeId, name);

        this.employeeId = employeeId;
        rooms = new ArrayList<Room>();
        reservations = new ArrayList<Reservation>();

        // DEFAULT HOTEL ROOMS
        rooms.add(new Room("101", "Standard", 1500));
        rooms.add(new Room("102", "Standard", 1500));
        rooms.add(new Room("201", "Deluxe", 2500));
        rooms.add(new Room("202", "Deluxe", 2500));
        rooms.add(new Room("301", "Premium", 4000));
        rooms.add(new Room("302", "Premium", 4000));
        rooms.add(new Room("401", "Suite", 6500));
    }

    // GET EMPLOYEE ID
    public String getEmployeeId() {
        return employeeId;
    }

    // GET ROLE
    @Override
    public String getRole() {
        return "Receptionist";
    }

    // GET ALL ROOMS
    public ArrayList<Room> getRooms() {
        return new ArrayList<Room>(rooms);
    }

    // GET AVAILABLE ROOMS
    public ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> availableRooms = new ArrayList<Room>();

        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    // GET ALL RESERVATIONS
    public ArrayList<Reservation> getReservations() {
        return new ArrayList<Reservation>(reservations);
    }

    // CREATE RESERVATION
    public Reservation createReservation(
            Guest guest,
            Room room,
            String checkIn,
            String checkOut) {

        if (guest == null || room == null || !room.isAvailable()) {
            return null;
        }

        String reservationId =
                String.format("RSV%03d", nextReservationNumber);

        Reservation reservation = new Reservation(
                reservationId, guest, room, checkIn, checkOut);

        reservation.createReservation();
        reservations.add(reservation);
        nextReservationNumber++;

        return reservation;
    }

    // FIND RESERVATION
    public Reservation findReservation(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId()
                    .equalsIgnoreCase(reservationId)) {
                return reservation;
            }
        }

        return null;
    }

    // CANCEL RESERVATION
    public boolean cancelReservation(String reservationId) {
        Reservation reservation = findReservation(reservationId);

        if (reservation == null
                || !reservation.getStatus().equals("Confirmed")) {
            return false;
        }

        reservation.cancelReservation();
        return true;
    }

    // ADD ROOM
    public boolean addRoom(
            String roomNumber, String roomType, double price) {

        if (roomNumber == null || roomType == null
                || roomNumber.trim().isEmpty()
                || roomType.trim().isEmpty()
                || price <= 0
                || findRoom(roomNumber) != null) {
            return false;
        }

        rooms.add(new Room(
                roomNumber.trim(), roomType.trim(), price));

        return true;
    }

    // FIND ROOM
    public Room findRoom(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber()
                    .equalsIgnoreCase(roomNumber)) {
                return room;
            }
        }

        return null;
    }

    // UPDATE ROOM
    public boolean updateRoom(
            String roomNumber, String roomType, double price) {

        Room room = findRoom(roomNumber);

        if (room == null || roomType == null
                || roomType.trim().isEmpty() || price <= 0) {
            return false;
        }

        room.setRoomType(roomType.trim());
        room.setPricePerNight(price);

        return true;
    }

    // REMOVE ROOM
    public boolean removeRoom(String roomNumber) {
        Room room = findRoom(roomNumber);

        if (room == null || !room.isAvailable()) {
            return false;
        }

        return rooms.remove(room);
    }

    // MANAGE RESERVATION
    public void manageReservation() {
        System.out.println("Total reservations: "
                + reservations.size());

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
