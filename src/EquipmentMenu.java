import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EquipmentMenu {
    private Connection conn;

    public EquipmentMenu(Connection conn) {
        this.conn = conn;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Equipment Table:");
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
            PreparedStatement statement = this.conn.prepareStatement(
                    "INSERT INTO equipment (equip_type, order_id, weight, size, serial_number, year, manufacturer, equip_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            System.out.print("Equipment Type (int): ");
            statement.setInt(1, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Order ID: ");
            statement.setString(2, scanner.nextLine());

            System.out.print("Weight (int): ");
            statement.setInt(3, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Size (int): ");
            statement.setInt(4, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Serial Number: ");
            statement.setString(5, scanner.nextLine());

            System.out.print("Year (int): ");
            statement.setInt(6, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Manufacturer: ");
            statement.setString(7, scanner.nextLine());

            System.out.print("Equipment Status (int): ");
            statement.setInt(8, scanner.nextInt());
            scanner.nextLine();

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                System.out.println("Creating equipment entry failed");
            } else {
                System.out.println("Equipment entry created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "DELETE FROM equipment WHERE serial_number = ?");

            System.out.print("Serial Number: ");
            statement.setString(1, scanner.nextLine());

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("No entry found with that serial number");
            } else {
                System.out.println("Entry deleted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEntry(Scanner scanner) {
        try {
            PreparedStatement statement = this.conn.prepareStatement(
                    "UPDATE equipment SET equip_type = ?, order_id = ?, weight = ?, size = ?, year = ?, manufacturer = ?, equip_status = ? WHERE serial_number = ?");

            System.out.print("Serial Number: ");
            String serialNumber = scanner.nextLine();

            System.out.print("Equipment Type (int): ");
            statement.setInt(1, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Order ID: ");
            statement.setString(2, scanner.nextLine());

            System.out.print("Weight (int): ");
            statement.setInt(3, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Size (int): ");
            statement.setInt(4, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Year (int): ");
            statement.setInt(5, scanner.nextInt());
            scanner.nextLine();

            System.out.print("Manufacturer: ");
            statement.setString(6, scanner.nextLine());

            System.out.print("Equipment Status (int): ");
            statement.setInt(7, scanner.nextInt());
            scanner.nextLine();

            statement.setString(8, serialNumber);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No entry found with that serial number");
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
            Statement statement = this.conn.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * FROM equipment");

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
