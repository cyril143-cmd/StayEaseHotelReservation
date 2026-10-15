// ROOM CLASS

class Room {
    private String roomId;
    private String roomNumber;
    private String roomType;
    private double price;
    private boolean availability;

    // CONSTRUCTOR
    public Room(String roomNumber, String roomType, double price) {
        this.roomId = roomNumber;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.availability = true;
    }

    // GETTERS
    public String getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return price;
    }

    public boolean isAvailable() {
        return availability;
    }

    // SETTERS
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPricePerNight(double price) {
        this.price = price;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }

    // DISPLAY ROOM INFORMATION
    public void displayRoomInfo() {
        System.out.println("Room ID: " + roomId);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price per Night: P" + price);
        System.out.println("Availability: "
                + (availability ? "Available" : "Occupied"));
    }

    // UPDATE ROOM AVAILABILITY
    public void updateAvailability(boolean availability) {
        this.availability = availability;
    }

    // DISPLAY ROOM IN THE GUI
    @Override
    public String toString() {
        return roomNumber + " - " + roomType
                + " (P" + String.format("%.2f", price)
                + " per night)";
    }
}
