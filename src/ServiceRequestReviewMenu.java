import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ServiceRequestReviewMenu {
    private Connection connection;
    private String tableName = "service_request_review";

    public ServiceRequestReviewMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Service Request Review Table:");
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
            System.out.println("Enter member ID: ");
            String memberId = scanner.nextLine();
            System.out.println("Enter start date: ");
            String startDate = scanner.nextLine();
            System.out.println("Enter description: ");
            String description = scanner.nextLine();
            System.out.println("Enter score: ");
            int score = scanner.nextInt();

            String query = "INSERT INTO " + this.tableName
                    + " (member_id, start_date, description, score) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = this.connection
                    .prepareStatement(query);
            statement.setString(1, memberId);
            statement.setString(2, startDate);
            statement.setString(3, description);
            statement.setInt(4, score);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("New entry created successfully!");
            } else {
                System.out.println("No entry created.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            System.out.println("Enter member ID: ");
            String memberId = scanner.nextLine();
            System.out.println("Enter start date: ");
            String startDate = scanner.nextLine();
            System.out.println("Enter new description: ");
            String newDescription = scanner.nextLine();
            System.out.println("Enter new score: ");
            int newScore = scanner.nextInt();

            String query = "UPDATE " + this.tableName
                    + " SET description = ?, score = ? "
                    + "WHERE member_id = ? AND start_date = ?";
            PreparedStatement statement = this.connection
                    .prepareStatement(query);
            statement.setString(1, newDescription);
            statement.setInt(2, newScore);
            statement.setString(3, memberId);
            statement.setString(4, startDate);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Entry updated successfully!");
            } else {
                System.out.println("No entry updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            System.out.println("Enter member ID: ");
            String memberId = scanner.nextLine();
            System.out.println("Enter start date: ");
            String startDate = scanner.nextLine();

            String query = "DELETE FROM " + this.tableName
                    + " WHERE member_id = ? AND start_date = ?";
            PreparedStatement statement = this.connection
                    .prepareStatement(query);
            statement.setString(1, memberId);
            statement.setString(2, startDate);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Entry deleted successfully!");
            } else {
                System.out.println("No entry deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM service_request_review");

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