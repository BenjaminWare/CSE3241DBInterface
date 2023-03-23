import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DroneMenu {
    private final Connection conn;

    public DroneMenu(Connection connection) {
        this.conn = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Drone Table:");
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

    private void updateEntry(Scanner scanner) {
        System.out.print("Enter the fleet id of the drone to update: ");
        String fleetId = scanner.nextLine();
        try {
            // Check if the drone exists in the database
            PreparedStatement checkStatement = this.conn
                    .prepareStatement("SELECT * FROM drone WHERE fleet_id = ?");
            checkStatement.setString(1, fleetId);
            ResultSet checkResult = checkStatement.executeQuery();

            if (!checkResult.next()) {
                System.out.println(
                        "Drone with fleet id " + fleetId + " does not exist.");
                return;
            }

            // Get new data for the drone
            System.out.print("Enter new capacity: ");
            int capacity = scanner.nextInt();
            System.out.print("Enter new volume capacity: ");
            int volumeCapacity = scanner.nextInt();
            System.out.print("Enter new distance autonomy: ");
            int distanceAutonomy = scanner.nextInt();
            System.out.print("Enter new max speed: ");
            int maxSpeed = scanner.nextInt();
            System.out.print("Enter new drone status: ");
            int droneStatus = scanner.nextInt();

            // Update the drone in the database
            PreparedStatement updateStatement = this.conn.prepareStatement(
                    "UPDATE drone SET capacity = ?, volume_capacity = ?, distance_autonomy = ?, max_speed = ?, drone_status = ? WHERE fleet_id = ?");
            updateStatement.setInt(1, capacity);
            updateStatement.setInt(2, volumeCapacity);
            updateStatement.setInt(3, distanceAutonomy);
            updateStatement.setInt(4, maxSpeed);
            updateStatement.setInt(5, droneStatus);
            updateStatement.setString(6, fleetId);
            int numRowsUpdated = updateStatement.executeUpdate();

            System.out.println(numRowsUpdated + " row(s) updated.");
        } catch (SQLException e) {
            System.out.println("Error updating drone: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM drone");
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

    private void createEntry(Scanner scanner) {
        try {
            // Get user inputs
            System.out.print("Enter fleet ID: ");
            String fleetId = scanner.nextLine().trim();
            System.out.print("Enter capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter volume capacity: ");
            int volumeCapacity = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter distance autonomy: ");
            int distanceAutonomy = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter maximum speed: ");
            int maxSpeed = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter drone status: ");
            int droneStatus = Integer.parseInt(scanner.nextLine().trim());

            // Insert new row into drone table
            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO drone (fleet_id, capacity, volume_capacity, distance_autonomy, max_speed, drone_status) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, fleetId);
            stmt.setInt(2, capacity);
            stmt.setInt(3, volumeCapacity);
            stmt.setInt(4, distanceAutonomy);
            stmt.setInt(5, maxSpeed);
            stmt.setInt(6, droneStatus);
            stmt.executeUpdate();

            System.out.println("New drone entry created successfully.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            // Get user input
            System.out.print("Enter fleet ID to delete: ");
            String fleetId = scanner.nextLine().trim();

            // Delete row from drone table
            PreparedStatement stmt = this.conn
                    .prepareStatement("DELETE FROM drone WHERE fleet_id = ?");
            stmt.setString(1, fleetId);
            int rowCount = stmt.executeUpdate();

            if (rowCount == 0) {
                System.out.println(
                        "No drone entry found with fleet ID " + fleetId);
            } else {
                System.out.println(
                        rowCount + " drone entry(s) deleted successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
