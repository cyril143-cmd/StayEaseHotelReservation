import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// MAIN CLASS AND JAVA SWING GUI
public class HotelReservationSystem extends JFrame {
    private Receptionist recep;

    public HotelReservationSystem() {
        recep = new Receptionist("R001", "Front Desk");

        setTitle("HotelReservationSystem - Hotel Reservation System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
            new BorderLayout(10, 15));

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel title = new JLabel(
            "STAYEASE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel subtitle = new JLabel(
            "Hotel Reservation and Management System",
            SwingConstants.CENTER);

        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.add(title);
        heading.add(subtitle);

        JPanel buttons = new JPanel(
            new GridLayout(6, 1, 10, 10));

        JButton makeButton =
            new JButton("1. Make Reservation");
        JButton availabilityButton =
            new JButton("2. Check Availability");
        JButton displayButton =
            new JButton("3. Display Reservations");
        JButton cancelButton =
            new JButton("4. Cancel Reservation");
        JButton manageButton =
            new JButton("5. Manage Rooms");
        JButton exitButton =
            new JButton("6. Exit");

        buttons.add(makeButton);
        buttons.add(availabilityButton);
        buttons.add(displayButton);
        buttons.add(cancelButton);
        buttons.add(manageButton);
        buttons.add(exitButton);

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(buttons, BorderLayout.CENTER);

        add(mainPanel);

        makeButton.addActionListener(
            e -> makeReservation());

        availabilityButton.addActionListener(
            e -> checkAvailability());

        displayButton.addActionListener(
            e -> displayReservations());

        cancelButton.addActionListener(
            e -> cancelReservation());

        manageButton.addActionListener(
            e -> manageRooms());

        exitButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    // MAKE RESERVATION
    private void makeReservation() {
        ArrayList<Room> available =
            recep.getAvailableRooms();

        if (available.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "No rooms are currently available.");
            return;
        }

        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField checkInField = new JTextField();
        JTextField checkOutField = new JTextField();

        JComboBox<Room> roomBox =
            new JComboBox<Room>(
                available.toArray(new Room[0]));

        JPanel form = new JPanel(
            new GridLayout(5, 2, 8, 8));

        form.add(new JLabel("Guest Name:"));
        form.add(nameField);

        form.add(new JLabel("Contact Number:"));
        form.add(contactField);

        form.add(new JLabel("Select Room:"));
        form.add(roomBox);

        form.add(new JLabel("Check-in Date:"));
        form.add(checkInField);

        form.add(new JLabel("Check-out Date:"));
        form.add(checkOutField);

        int answer = JOptionPane.showConfirmDialog(
            this, form, "Make Reservation",
            JOptionPane.OK_CANCEL_OPTION);

        if (answer != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String checkIn = checkInField.getText().trim();
        String checkOut = checkOutField.getText().trim();

        if (name.isEmpty() || contact.isEmpty()
                || checkIn.isEmpty() || checkOut.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please complete all fields.");
            return;
        }

        if (!contact.matches("09\\d{9}")) {
            JOptionPane.showMessageDialog(
                this,
                "Enter a valid 11-digit mobile number starting with 09.");
            return;
        }

        Guest guest = new Guest(name, contact);
         Room room = (Room) roomBox.getSelectedItem();

        Reservation reservation =
         guest.makeReservation(
        recep, room, checkIn, checkOut);
        
        if (reservation == null) {
            JOptionPane.showMessageDialog(
                this, "This room is no longer available.");
            return;
        }

        JOptionPane.showMessageDialog(
            this,
            "Reservation Successful!\n\n"
                + reservation
                + "\nContact: " + guest.getContactNumber()
                + "\nPrice per night: P"
                + String.format("%.2f",
                    room.getPricePerNight()));
    }

    // CHECK AVAILABILITY
    private void checkAvailability() {
        StringBuilder result =
            new StringBuilder("ROOM AVAILABILITY\n\n");

        for (Room room : recep.getRooms()) {
            result.append(room.getRoomNumber())
                .append(" - ")
                .append(room.getRoomType())
                .append(" - P")
                .append(String.format("%.2f",
                    room.getPricePerNight()))
                .append(" - ")
                .append(room.isAvailable()
                    ? "Available" : "Occupied")
                .append("\n");
        }

        JTextArea area = new JTextArea(result.toString());
        area.setEditable(false);
        area.setRows(10);
        area.setColumns(35);

        JOptionPane.showMessageDialog(
            this, new JScrollPane(area),
            "Check Availability",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // DISPLAY RESERVATIONS
    private void displayReservations() {
        ArrayList<Reservation> records =
            recep.getReservations();

        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "No reservations found.");
            return;
        }

        StringBuilder result =
            new StringBuilder("RESERVATION RECORDS\n\n");

        for (Reservation reservation : records) {
            result.append(reservation).append("\n\n");
        }

        JTextArea area = new JTextArea(result.toString());
        area.setEditable(false);
        area.setRows(12);
        area.setColumns(55);

        JOptionPane.showMessageDialog(
            this, new JScrollPane(area),
            "Display Reservations",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // CANCEL RESERVATION
    private void cancelReservation() {
        String id = JOptionPane.showInputDialog(
            this, "Enter Reservation ID (e.g., RSV001):");

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        Reservation reservation =
            recep.findReservation(id.trim());

        if (reservation == null
                || !reservation.getStatus().equals("Confirmed")) {
            JOptionPane.showMessageDialog(
                this, "Active reservation not found.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
            this,
            "Cancel this reservation?\n\n" + reservation,
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            boolean cancelled =
                recep.cancelReservation(id);

            if (cancelled) {
                JOptionPane.showMessageDialog(
                    this, "Reservation Cancelled!");
            }
        }
    }

    // MANAGE ROOMS
    private void manageRooms() {
        String[] options = {
            "Add Room", "Update Room",
            "Remove Room", "Cancel"
        };

        int choice = JOptionPane.showOptionDialog(
            this,
            "Choose a room management action:",
            "Manage Rooms",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        switch (choice) {
            case 0:
                addRoom();
                break;

            case 1:
                updateRoom();
                break;

            case 2:
                removeRoom();
                break;

            default:
                break;
        }
    }

    // ADD ROOM
    private void addRoom() {
        String number = JOptionPane.showInputDialog(
            this, "Enter room number:");

        if (number == null || number.trim().isEmpty()) {
            return;
        }

        String type = JOptionPane.showInputDialog(
            this, "Enter room type:");

        if (type == null || type.trim().isEmpty()) {
            return;
        }

        String priceText = JOptionPane.showInputDialog(
            this, "Enter price per night:");

        if (priceText == null) {
            return;
        }

        try {
            double price = Double.parseDouble(priceText);

            if (recep.addRoom(number, type, price)) {
                JOptionPane.showMessageDialog(
                    this, "Room added successfully!");
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Unable to add room. Check the room number and price.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid price.");
        }
    }

    // UPDATE ROOM
    private void updateRoom() {
        String number = JOptionPane.showInputDialog(
            this, "Enter room number to update:");

        if (number == null || number.trim().isEmpty()) {
            return;
        }

        String type = JOptionPane.showInputDialog(
            this, "Enter new room type:");

        if (type == null || type.trim().isEmpty()) {
            return;
        }

        String priceText = JOptionPane.showInputDialog(
            this, "Enter new price per night:");

        if (priceText == null) {
            return;
        }

        try {
            double price = Double.parseDouble(priceText);

            if (recep.updateRoom(number, type, price)) {
                JOptionPane.showMessageDialog(
                    this, "Room updated successfully!");
            } else {
                JOptionPane.showMessageDialog(
                    this, "Room not found or invalid information.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid price.");
        }
    }

    // REMOVE ROOM
    private void removeRoom() {
        String number = JOptionPane.showInputDialog(
            this, "Enter room number to remove:");

        if (number == null || number.trim().isEmpty()) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
            this,
            "Remove room " + number + "?",
            "Confirm Remove",
            JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            if (recep.removeRoom(number)) {
                JOptionPane.showMessageDialog(
                    this, "Room removed successfully!");
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Room not found or currently occupied.");
            }
        }
    }

    // RUN THE PROGRAM
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelReservationSystem app = new HotelReservationSystem();
            app.setVisible(true);
        });
    }
}
