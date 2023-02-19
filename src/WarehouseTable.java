import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class WarehouseTable {
    private HashSet<Warehouse> warehouses;

    public WarehouseTable() {
        this.warehouses = new HashSet<>();
    }

    public void createWarehouse(Scanner scanner) {
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter manager name: ");
        String managerName = scanner.nextLine();

        int storageCapacity;
        do {
            System.out.print("Enter storage capacity: ");
            while (!scanner.hasNextInt()) {
                System.out.println(
                        "Invalid input. Please enter an integer value.");
                scanner.next();
            }
            storageCapacity = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
        } while (storageCapacity <= 0);

        int droneCapacity;
        do {
            System.out.print("Enter drone capacity: ");
            while (!scanner.hasNextInt()) {
                System.out.println(
                        "Invalid input. Please enter an integer value.");
                scanner.next();
            }
            droneCapacity = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
        } while (droneCapacity <= 0);

        Warehouse warehouse = new Warehouse(city, address, phoneNumber,
                managerName, storageCapacity, droneCapacity);
        this.warehouses.add(warehouse);
    }

    public void printAllWarehouses() {
        if (this.warehouses.isEmpty()) {
            System.out.println("No Warehouses to display.");

        } else {
            System.out.println("Warehouse List:");
            for (Warehouse warehouse : this.warehouses) {
                System.out.println(warehouse.toString());
            }
        }
    }

    public void deleteWarehouseByAddressInput(Scanner scanner) {

        while (true) {
            System.out.print(
                    "Enter the ID of the warehouse to delete (or 'q' to quit): ");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                break;
            }
            this.deleteWarehouseByAddress(input);
        }

    }

    private void deleteWarehouseByAddress(String address) {
        Iterator<Warehouse> iterator = this.warehouses.iterator();
        while (iterator.hasNext()) {
            Warehouse warehouse = iterator.next();
            if (warehouse.address.equals(address)) {
                iterator.remove();
                System.out.println("Warehouse at address " + address
                        + " has been deleted.");
                return;
            }
        }
        System.out.println("Warehouse at address " + address + " not found.");
    }
}
