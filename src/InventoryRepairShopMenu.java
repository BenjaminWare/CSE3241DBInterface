import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InventoryRepairShopMenu {
    private Connection connection;

    public InventoryRepairShopMenu(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inventory Repair Shop Table:");
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
            System.out.print("Enter repair shop address: ");
            String address = scanner.nextLine();

            System.out.print("Enter repair shop name: ");
            String name = scanner.nextLine();

            System.out.print("Enter repair shop manager: ");
            String manager = scanner.nextLine();

            String sql = "INSERT INTO inventory_repair_shop(address, name, manager) VALUES (?, ?, ?)";

            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, address);
            statement.setString(2, name);
            statement.setString(3, manager);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new repair shop has been created.");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error creating repair shop: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            System.out.print(
                    "Enter the address of the repair shop you want to delete: ");
            String address = scanner.nextLine();

            String sql = "DELETE FROM inventory_repair_shop WHERE address = ?";

            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, address);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The repair shop has been deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting repair shop: " + e.getMessage());
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            System.out.print(
                    "Enter the address of the repair shop you want to update: ");
            String address = scanner.nextLine();

            System.out.print("Enter the new name of the repair shop: ");
            String name = scanner.nextLine();

            System.out.print("Enter the new manager of the repair shop: ");
            String manager = scanner.nextLine();

            String sql = "UPDATE inventory_repair_shop SET name = ?, manager = ? WHERE address = ?";

            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, manager);
            statement.setString(3, address);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The repair shop has been updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating repair shop: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM inventory_repair_shop");

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
