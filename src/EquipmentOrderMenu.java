import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EquipmentOrderMenu {
    private final Connection connection;

    public EquipmentOrderMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Equipment Order Table:");
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
            System.out.println("Please enter the order ID:");
            String orderID = scanner.nextLine();

            System.out.println("Please enter the warehouse address:");
            String warehouseAddress = scanner.nextLine();

            System.out.println("Please enter the status:");
            int status = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Please enter the ETA:");
            String ETA = scanner.nextLine();

            System.out.println("Please enter the order date:");
            String orderDate = scanner.nextLine();

            System.out.println("Please enter the arrival date:");
            String arrivalDate = scanner.nextLine();

            String query = "INSERT INTO equipment_order (order_id, warehouse_address, status, ETA, order_date, arrival_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = this.connection
                    .prepareStatement(query);
            statement.setString(1, orderID);
            statement.setString(2, warehouseAddress);
            statement.setInt(3, status);
            statement.setString(4, ETA);
            statement.setString(5, orderDate);
            statement.setString(6, arrivalDate);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Equipment order inserted successfully");
            } else {
                System.out.println("Failed to insert equipment order");
            }
        } catch (SQLException e) {
            System.out.println(
                    "Failed to insert equipment order: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            System.out.println(
                    "Please enter the order ID of the equipment order you wish to delete:");
            String orderID = scanner.nextLine();

            String query = "DELETE FROM equipment_order WHERE order_id = ?";
            PreparedStatement statement = this.connection
                    .prepareStatement(query);
            statement.setString(1, orderID);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Equipment order deleted successfully");
            } else {
                System.out.println("Failed to delete equipment order");
            }
        } catch (SQLException e) {
            System.out.println(
                    "Failed to delete equipment order: " + e.getMessage());
        }
    }

    /**
     * Updates an existing entry in the equipment_order table in the database.
     * Prompts the user to enter the order_id of the entry to update, and then
     * prompts the user for the new values for each field.
     *
     * @param scanner
     *            the scanner object to get user input
     * @throws SQLException
     *             if a database access error occurs
     */
    private void updateEntry(Scanner scanner) {
        try {
            System.out.print("Enter the order_id of the entry to update: ");
            String order_id = scanner.nextLine();

            PreparedStatement preparedStatement = this.connection
                    .prepareStatement(
                            "UPDATE equipment_order SET warehouse_address = ?, status = ?, ETA = ?, arrival_date = ? WHERE order_id = ?");
            System.out.print("Enter the new warehouse_address: ");
            String warehouse_address = scanner.nextLine();
            preparedStatement.setString(1, warehouse_address);
            System.out.print("Enter the new status: ");
            int status = Integer.parseInt(scanner.nextLine());
            preparedStatement.setInt(2, status);
            System.out.print("Enter the new ETA: ");
            String ETA = scanner.nextLine();
            preparedStatement.setString(3, ETA);
            System.out.print("Enter the new arrival_date: ");
            String arrival_date = scanner.nextLine();
            preparedStatement.setString(4, arrival_date);
            preparedStatement.setString(5, order_id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all entries in the equipment_order table from the database and
     * prints them to the console.
     *
     * @throws SQLException
     *             if a database access error occurs
     */
    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM equipment_order");

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
