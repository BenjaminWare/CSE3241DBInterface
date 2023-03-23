import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ReturnMenu {

    private Connection connection;

    public ReturnMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Return Table:");
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
        System.out.println("Enter start date:");
        String startDate = scanner.nextLine();
        System.out.println("Enter fleet ID:");
        String fleetId = scanner.nextLine();

        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO return (member_id, start_date, fleet_id) "
                            + "VALUES (?, ?, ?)");

            statement.setString(1, memberId);
            statement.setString(2, startDate);
            statement.setString(3, fleetId);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Return entry added.");
            } else {
                System.out.println("Error: Return entry not added.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Update a specific entry in the database
    private void updateEntry(Scanner scanner) {
        System.out.print("Enter the member ID of the return to update: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter the start date of the return to update: ");
        String startDate = scanner.nextLine();
        System.out.print("Enter the fleet ID of the return to update: ");
        String fleetId = scanner.nextLine();

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM return WHERE member_id = '"
                            + memberId + "' AND start_date = '" + startDate
                            + "' AND fleet_id = '" + fleetId + "'");

            if (resultSet.next()) {
                System.out.println("Current information: ");
                System.out.printf(
                        "Member ID: %s, Start Date: %s, Fleet ID: %s%n",
                        resultSet.getString("member_id"),
                        resultSet.getString("start_date"),
                        resultSet.getString("fleet_id"));
                System.out.printf("Return Date: %s, Return Location: %s%n",
                        resultSet.getString("return_date"),
                        resultSet.getString("return_location"));
                System.out.print(
                        "Enter the new return date (or press enter to keep current value): ");
                String newReturnDate = scanner.nextLine();
                System.out.print(
                        "Enter the new return location (or press enter to keep current value): ");
                String newReturnLocation = scanner.nextLine();

                String updateQuery = "UPDATE return SET ";
                if (!newReturnDate.isEmpty()) {
                    updateQuery += "return_date = '" + newReturnDate + "'";
                }
                if (!newReturnLocation.isEmpty()) {
                    if (!newReturnDate.isEmpty()) {
                        updateQuery += ", ";
                    }
                    updateQuery += "return_location = '" + newReturnLocation
                            + "'";
                }
                updateQuery += " WHERE member_id = '" + memberId
                        + "' AND start_date = '" + startDate
                        + "' AND fleet_id = '" + fleetId + "'";

                statement.executeUpdate(updateQuery);
                System.out.println("Return successfully updated!");
            } else {
                System.out.println(
                        "No return found with the given member ID, start date, and fleet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating the return: "
                    + e.getMessage());
        }
    }

    // Delete a specific entry from the database
    private void deleteEntry(Scanner scanner) {
        System.out.print("Enter the member ID of the return to delete: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter the start date of the return to delete: ");
        String startDate = scanner.nextLine();
        System.out.print("Enter the fleet ID of the return to delete: ");
        String fleetId = scanner.nextLine();

        try {
            Statement statement = this.connection.createStatement();
            int rowsAffected = statement
                    .executeUpdate("DELETE FROM return WHERE member_id = '"
                            + memberId + "' AND start_date = '" + startDate
                            + "' AND fleet_id = '" + fleetId + "'");

            if (rowsAffected > 0) {
                System.out.println("Return successfully deleted!");
            } else {
                System.out.println(
                        "No return found with the given member ID, start date, and fleet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting the return: "
                    + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM return");

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
