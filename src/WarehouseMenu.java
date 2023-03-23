import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WarehouseMenu {
    private Connection connection;

    public WarehouseMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Warehouse Table:");
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
            PreparedStatement statement = this.connection.prepareStatement(
                    "INSERT INTO warehouse (address, city, manager_name, storage_capacity, drone_capacity) "
                            + "VALUES (?, ?, ?, ?, ?)");
            System.out.println("Enter address (primary key):");
            statement.setString(1, scanner.nextLine());
            System.out.println("Enter city:");
            statement.setString(2, scanner.nextLine());
            System.out.println("Enter manager name:");
            statement.setString(3, scanner.nextLine());
            System.out.println("Enter storage capacity:");
            statement.setInt(4, Integer.parseInt(scanner.nextLine()));
            System.out.println("Enter drone capacity:");
            statement.setInt(5, Integer.parseInt(scanner.nextLine()));
            int rowsInserted = statement.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM warehouse");

            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String startDate = resultSet.getString("start_date");
                int statusId = resultSet.getInt("status_id");

                System.out.printf("%s | %s | %s | %s | %s | %s | %s | %d\n",
                        userId, firstName, lastName, address, phone, email,
                        startDate, statusId);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            System.out.println("Enter the address of the warehouse to update:");
            String address = scanner.nextLine();
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE warehouse SET city = ?, manager_name = ?, storage_capacity = ?, drone_capacity = ? "
                            + "WHERE address = ?");
            System.out.println("Enter new city:");
            statement.setString(1, scanner.nextLine());
            System.out.println("Enter new manager name:");
            statement.setString(2, scanner.nextLine());
            System.out.println("Enter new storage capacity:");
            statement.setInt(3, Integer.parseInt(scanner.nextLine()));
            System.out.println("Enter new drone capacity:");
            statement.setInt(4, Integer.parseInt(scanner.nextLine()));
            statement.setString(5, address);
            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " row(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            System.out.println("Enter the address of the warehouse to delete:");
            String address = scanner.nextLine();
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM warehouse WHERE address = ?");
            statement.setString(1, address);
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " row(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
