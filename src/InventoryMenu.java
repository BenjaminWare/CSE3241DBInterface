import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InventoryMenu {
    private final Connection connection;

    public InventoryMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inventory Table:");
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
                    "INSERT INTO inventory (inventory_id, warehouse_address, description, type, fleet_id, serial_number) "
                            + "VALUES (?, ?, ?, ?, ?, ?)");

            System.out.print("Inventory ID: ");
            statement.setString(1, scanner.nextLine());

            System.out.print("Warehouse address: ");
            statement.setString(2, scanner.nextLine());

            System.out.print("Description: ");
            statement.setString(3, scanner.nextLine());

            System.out.print("Type: ");
            statement.setInt(4, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Fleet ID: ");
            statement.setString(5, scanner.nextLine());

            System.out.print("Serial number: ");
            statement.setString(6, scanner.nextLine());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                System.out.println("Entry creation failed");
            } else {
                System.out.println("Entry created successfully");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM inventory WHERE inventory_id = ?");

            System.out.print("Inventory ID: ");
            statement.setString(1, scanner.nextLine());

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("No entry found with that inventory ID");
            } else {
                System.out.println("Entry deleted successfully");
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE inventory SET warehouse_address = ?, description = ?, type = ?, fleet_id = ?, serial_number = ? WHERE inventory_id = ?");

            System.out.print("Inventory ID: ");
            String inventoryId = scanner.nextLine();

            System.out.print("Warehouse Address: ");
            statement.setString(1, scanner.nextLine());

            System.out.print("Description: ");
            statement.setString(2, scanner.nextLine());

            System.out.print("Type: ");
            statement.setInt(3, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Fleet ID: ");
            statement.setString(4, scanner.nextLine());

            System.out.print("Serial Number: ");
            statement.setString(5, scanner.nextLine());

            statement.setString(6, inventoryId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No entry found with that inventory ID");
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
                    .executeQuery("SELECT * FROM inventory");

            while (resultSet.next()) {
                String inventoryId = resultSet.getString("inventory_id");
                String warehouseAddress = resultSet
                        .getString("warehouse_address");
                String description = resultSet.getString("description");
                int type = resultSet.getInt("type");
                String fleetId = resultSet.getString("fleet_id");
                String serialNumber = resultSet.getString("serial_number");

                System.out.printf("%s | %s | %s | %d | %s | %s\n", inventoryId,
                        warehouseAddress, description, type, fleetId,
                        serialNumber);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}