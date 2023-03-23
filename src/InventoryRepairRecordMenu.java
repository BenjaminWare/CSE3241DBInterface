import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InventoryRepairRecordMenu {
    private Connection connection;
    private Scanner scanner;

    public InventoryRepairRecordMenu(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inventory Repair Record Table:");
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
                    "INSERT INTO inventory_repair_record (repair_shop_address, date, cost) VALUES (?, ?, ?)");
            System.out.print("Enter repair shop address: ");
            statement.setString(1, scanner.nextLine());
            System.out.print("Enter date: ");
            statement.setString(2, scanner.nextLine());
            System.out.print("Enter cost: ");
            statement.setInt(3, scanner.nextInt());
            scanner.nextLine();
            statement.executeUpdate();
            System.out.println("Record added successfully");
        } catch (SQLException e) {
            System.out.println("Error creating entry: " + e.getMessage());
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "UPDATE inventory_repair_record SET cost=? WHERE repair_shop_address=? AND date=?");
            System.out.print("Enter repair shop address: ");
            statement.setString(2, scanner.nextLine());
            System.out.print("Enter date: ");
            statement.setString(3, scanner.nextLine());
            System.out.print("Enter new cost: ");
            statement.setInt(1, scanner.nextInt());
            scanner.nextLine();
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println(
                        "No records found for the given repair shop address and date");
            } else {
                System.out.println("Record updated successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error updating entry: " + e.getMessage());
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "DELETE FROM inventory_repair_record WHERE repair_shop_address=? AND date=?");
            System.out.print("Enter repair shop address: ");
            statement.setString(1, scanner.nextLine());
            System.out.print("Enter date: ");
            statement.setString(2, scanner.nextLine());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println(
                        "No records found for the given repair shop address and date");
            } else {
                System.out.println("Record deleted successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting entry: " + e.getMessage());
        }
    }

    private void readEntries() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM inventory_repair_record");

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