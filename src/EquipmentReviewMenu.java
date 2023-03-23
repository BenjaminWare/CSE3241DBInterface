import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class EquipmentReviewMenu {
    private Connection connection;
    private String tableName = "service_request_review";

    public EquipmentReviewMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Equipment Review Table:");
            System.out.println(
                    "Enter an option (c)reate, (r)ead, (u)pdate, (d)elete, or (q)uit:");
            String option = scanner.nextLine();
            if (option.equals("q")) {
                break;
            }
            switch (option) {
                case "c":
                    this.createEntry(scanner);
                    break;
                case "r":
                    this.readEntries();
                    break;
                case "u":
                    this.updateEntry(scanner);
                    break;
                case "d":
                    this.deleteEntry(scanner);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void createEntry(Scanner scanner) {
        System.out.println("Enter member ID:");
        String memberId = scanner.nextLine();
        System.out.println("Enter serial number:");
        String serialNumber = scanner.nextLine();
        System.out.println("Enter description:");
        String description = scanner.nextLine();
        System.out.println("Enter score:");
        int score = scanner.nextInt();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(
                    "INSERT INTO equipment_review (member_id, serial_number, description, score) VALUES (?, ?, ?, ?)");
            stmt.setString(1, memberId);
            stmt.setString(2, serialNumber);
            stmt.setString(3, description);
            stmt.setInt(4, score);
            stmt.executeUpdate();
            System.out.println("Equipment review created successfully.");
        } catch (SQLException e) {
            System.out.println(
                    "Failed to create equipment review: " + e.getMessage());
        }
    }

    private void updateEntry(Scanner scanner) {
        System.out.println("Enter member ID:");
        String memberId = scanner.nextLine();
        System.out.println("Enter serial number:");
        String serialNumber = scanner.nextLine();
        System.out.println(
                "Enter new description (or leave blank to keep current description):");
        String description = scanner.nextLine();
        System.out.println(
                "Enter new score (or leave blank to keep current score):");
        String scoreStr = scanner.nextLine();
        int score = -1;
        if (!scoreStr.isEmpty()) {
            try {
                score = Integer.parseInt(scoreStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid score: " + scoreStr);
                return;
            }
        }

        try {
            PreparedStatement stmt = this.connection.prepareStatement(
                    "UPDATE equipment_review SET description = ?, score = ? WHERE member_id = ? AND serial_number = ?");
            stmt.setString(1, description);
            if (score != -1) {
                stmt.setInt(2, score);
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, memberId);
            stmt.setString(4, serialNumber);
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                System.out.println("No equipment review found with member ID "
                        + memberId + " and serial number " + serialNumber);
            } else {
                System.out.println("Equipment review updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println(
                    "Failed to update equipment review: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            // Get primary key value from user input
            System.out.print("Enter the serial number to delete: ");
            String primaryKeyValue = scanner.nextLine();

            // Check if the entry exists
            String selectQuery = "SELECT * FROM equipment WHERE serial_number = ?";
            PreparedStatement selectStmt = this.connection
                    .prepareStatement(selectQuery);
            selectStmt.setString(1, primaryKeyValue);
            ResultSet selectResult = selectStmt.executeQuery();

            // If the entry exists, delete it
            if (selectResult.next()) {
                String deleteQuery = "DELETE FROM equipment WHERE serial_number = ?";
                PreparedStatement deleteStmt = this.connection
                        .prepareStatement(deleteQuery);
                deleteStmt.setString(1, primaryKeyValue);
                deleteStmt.executeUpdate();
                System.out.println("Entry deleted successfully.");
            } else {
                System.out.println("Entry does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting entry: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM equipment_review");

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print header row
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print data rows
            while (result.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(result.getString(i) + "\t");
                }
                System.out.println();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}