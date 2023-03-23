import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeliveryMenu {
    private Connection conn;

    public DeliveryMenu(Connection conn) {
        this.conn = conn;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Delivery Table:");
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
        try {
            System.out.print("Enter member ID: ");
            String member_id = scanner.nextLine();

            System.out.print("Enter start date: ");
            String start_date = scanner.nextLine();

            System.out.print("Enter fleet ID: ");
            String fleet_id = scanner.nextLine();

            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO delivery (member_id, start_date, fleet_id) VALUES (?, ?, ?)");
            stmt.setString(1, member_id);
            stmt.setString(2, start_date);
            stmt.setString(3, fleet_id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Delivery record created successfully.");
            } else {
                System.out.println("Failed to create delivery record.");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            System.out.print("Enter member ID of delivery to update: ");
            String member_id = scanner.nextLine();

            System.out.print("Enter start date of delivery to update: ");
            String start_date = scanner.nextLine();

            System.out.print("Enter fleet ID of delivery to update: ");
            String fleet_id = scanner.nextLine();

            System.out.print("Enter new fleet ID: ");
            String new_fleet_id = scanner.nextLine();

            PreparedStatement stmt = this.conn.prepareStatement(
                    "UPDATE delivery SET fleet_id = ? WHERE member_id = ? AND start_date = ? AND fleet_id = ?");
            stmt.setString(1, new_fleet_id);
            stmt.setString(2, member_id);
            stmt.setString(3, start_date);
            stmt.setString(4, fleet_id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Delivery record updated successfully.");
            } else {
                System.out.println("Failed to update delivery record.");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        System.out.println("Enter member ID: ");
        String memberID = scanner.nextLine().trim();
        System.out.println("Enter start date: ");
        String startDate = scanner.nextLine().trim();
        System.out.println("Enter fleet ID: ");
        String fleetID = scanner.nextLine().trim();

        try {
            PreparedStatement stmt = this.conn.prepareStatement(
                    "DELETE FROM delivery WHERE member_id = ? AND start_date = ? AND fleet_id = ?");
            stmt.setString(1, memberID);
            stmt.setString(2, startDate);
            stmt.setString(3, fleetID);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Entry deleted successfully.");
            } else {
                System.out.println("No entry found with specified parameters.");
            }
        } catch (SQLException e) {
            System.out.println(
                    "Error deleting entry from database: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM delivery");

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
