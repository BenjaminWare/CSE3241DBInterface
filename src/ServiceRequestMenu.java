import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ServiceRequestMenu {
    private Connection conn;

    public ServiceRequestMenu(Connection conn) {
        this.conn = conn;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Service Request Table:");
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

    private void createEntry(Scanner sc) {
        try {
            PreparedStatement ps = this.conn.prepareStatement(
                    "INSERT INTO service_request (member_id, status, start_date, end_date, fee, fee_status) VALUES (?, ?, ?, ?, ?, ?)");

            System.out.print("Enter member ID: ");
            String memberId = sc.nextLine();
            ps.setString(1, memberId);

            System.out.print("Enter status: ");
            int status = sc.nextInt();
            ps.setInt(2, status);

            sc.nextLine();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = sc.nextLine();
            ps.setString(3, startDate);

            System.out.print("Enter end date (YYYY-MM-DD, optional): ");
            String endDate = sc.nextLine();
            if (endDate.isEmpty()) {
                ps.setNull(4, java.sql.Types.NULL);
            } else {
                ps.setString(4, endDate);
            }

            System.out.print("Enter fee: ");
            int fee = sc.nextInt();
            ps.setInt(5, fee);

            System.out.print("Enter fee status: ");
            int feeStatus = sc.nextInt();
            ps.setInt(6, feeStatus);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service request added successfully.");
            } else {
                System.out.println("Failed to add service request.");
            }
        } catch (SQLException e) {
            System.out
                    .println("Error adding service request: " + e.getMessage());
        }
    }

    public void updateEntry(Scanner scanner) {
        System.out.print(
                "Enter the member ID of the service request to update: ");
        String memberId = scanner.nextLine();
        System.out.print(
                "Enter the start date of the service request to update: ");
        String startDate = scanner.nextLine();

        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "SELECT * FROM service_request WHERE member_id = ? AND start_date = ?");
            statement.setString(1, memberId);
            statement.setString(2, startDate);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Service request exists, update the fields
                System.out.println(
                        "Service request found. Enter new values (leave blank to keep current value):");

                // Get updated status
                System.out
                        .print("Status (" + resultSet.getInt("status") + "): ");
                String statusStr = scanner.nextLine();
                int status = statusStr.equals("") ? resultSet.getInt("status")
                        : Integer.parseInt(statusStr);

                // Get updated end date
                System.out.print(
                        "End date (" + resultSet.getString("end_date") + "): ");
                String endDate = scanner.nextLine();
                if (endDate.equals("")) {
                    endDate = resultSet.getString("end_date");
                }

                // Get updated fee
                System.out.print("Fee (" + resultSet.getInt("fee") + "): ");
                String feeStr = scanner.nextLine();
                int fee = feeStr.equals("") ? resultSet.getInt("fee")
                        : Integer.parseInt(feeStr);

                // Get updated fee status
                System.out.print("Fee status (" + resultSet.getInt("fee_status")
                        + "): ");
                String feeStatusStr = scanner.nextLine();
                int feeStatus = feeStatusStr.equals("")
                        ? resultSet.getInt("fee_status")
                        : Integer.parseInt(feeStatusStr);

                // Update the service request
                PreparedStatement updateStatement = this.conn.prepareStatement(
                        "UPDATE service_request SET status = ?, end_date = ?, fee = ?, fee_status = ? WHERE member_id = ? AND start_date = ?");
                updateStatement.setInt(1, status);
                updateStatement.setString(2, endDate);
                updateStatement.setInt(3, fee);
                updateStatement.setInt(4, feeStatus);
                updateStatement.setString(5, memberId);
                updateStatement.setString(6, startDate);
                updateStatement.executeUpdate();

                System.out.println("Service request updated successfully.");
            } else {
                System.out.println("Service request not found.");
            }
        } catch (SQLException e) {
            System.out.println(
                    "Error updating service request: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            // Prompt user for primary key values
            System.out.print("Enter member ID: ");
            String memberId = scanner.nextLine().trim();

            System.out.print("Enter start date: ");
            String startDate = scanner.nextLine().trim();

            System.out.print("Enter fleet ID: ");
            String fleetId = scanner.nextLine().trim();

            // Check if the entry exists
            PreparedStatement selectStatement = this.conn.prepareStatement(
                    "SELECT * FROM delivery WHERE member_id = ? AND start_date = ? AND fleet_id = ?");
            selectStatement.setString(1, memberId);
            selectStatement.setString(2, startDate);
            selectStatement.setString(3, fleetId);
            ResultSet selectResult = selectStatement.executeQuery();

            if (!selectResult.next()) {
                System.out.println("Entry not found.");
                return;
            }

            // Delete the entry
            PreparedStatement deleteStatement = this.conn.prepareStatement(
                    "DELETE FROM delivery WHERE member_id = ? AND start_date = ? AND fleet_id = ?");
            deleteStatement.setString(1, memberId);
            deleteStatement.setString(2, startDate);
            deleteStatement.setString(3, fleetId);
            deleteStatement.executeUpdate();

            System.out.println("Entry deleted successfully.");
            selectStatement.close();
            deleteStatement.close();
        } catch (SQLException e) {
            System.out.println("Error deleting entry: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.conn.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM service_request");

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
