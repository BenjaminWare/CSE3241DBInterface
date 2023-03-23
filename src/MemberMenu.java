import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MemberMenu {
    private final Connection connection;

    public MemberMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Member Table:");
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

    private void deleteEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection
                    .prepareStatement("DELETE FROM member WHERE user_id = ?");

            System.out.print("User ID: ");
            statement.setInt(1, scanner.nextInt());
            scanner.nextLine(); // Consume newline character

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("No entry found with that user ID");
            } else {
                System.out.println("Entry deleted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO member (user_id, first_name, last_name, address, phone, email, start_date, status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            System.out.print("User ID: ");
            statement.setInt(1, scanner.nextInt());
            scanner.nextLine(); // Consume newline character

            System.out.print("First name: ");
            statement.setString(2, scanner.nextLine());

            System.out.print("Last name: ");
            statement.setString(3, scanner.nextLine());

            System.out.print("Address: ");
            statement.setString(4, scanner.nextLine());

            System.out.print("Phone: ");
            statement.setString(5, scanner.nextLine());

            System.out.print("Email: ");
            statement.setString(6, scanner.nextLine());

            System.out.print("Start date: ");
            statement.setString(7, scanner.nextLine());

            System.out.print("Status ID: ");
            statement.setInt(8, scanner.nextInt());
            scanner.nextLine(); // Consume newline character

            statement.executeUpdate();

            System.out.println("Entry created successfully");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE member SET first_name = ?, last_name = ?, address = ?, phone = ?, email = ?, start_date = ?, status_id = ? WHERE user_id = ?");

            System.out.print("User ID: ");
            statement.setInt(8, scanner.nextInt());
            scanner.nextLine(); // Consume newline character

            System.out.print("First name: ");
            statement.setString(1, scanner.nextLine());

            System.out.print("Last name: ");
            statement.setString(2, scanner.nextLine());

            System.out.print("Address: ");
            statement.setString(3, scanner.nextLine());

            System.out.print("Phone: ");
            statement.setString(4, scanner.nextLine());

            System.out.print("Email: ");
            statement.setString(5, scanner.nextLine());

            System.out.print("Start date: ");
            statement.setString(6, scanner.nextLine());

            System.out.print("Status ID: ");
            statement.setInt(7, scanner.nextInt());
            scanner.nextLine(); // Consume newline character

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No entry found with that user ID");
            } else {
                System.out.println("Entry updated successfully");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM member");

            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String startDate = resultSet.getString("start_date");
                int statusId = resultSet.getInt("status_id");

                System.out.printf("%s | %s | %s | %s | %s | %s | %s | %d%n",
                        userId, firstName, lastName, address, phone, email,
                        startDate, statusId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
