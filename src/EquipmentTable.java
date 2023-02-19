import java.util.HashSet;
import java.util.Scanner;

public class EquipmentTable {
    private HashSet<Equipment> equipment;

    public EquipmentTable() {
        this.equipment = new HashSet<Equipment>();
    }

    public void addEquipment(Scanner scanner) {
        System.out.println("Enter equipment type:");
        String equipType = scanner.nextLine();
        System.out.println("Enter order id:");
        int orderId = 0;
        while (true) {
            try {
                orderId = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid orderID. Please enter an integer: ");
            }
        }
        System.out.println("Enter weight:");
        double weight = 0.0;
        while (true) {
            try {
                weight = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid weight. Please enter an double: ");
            }
        }
        System.out.println("Enter size:");
        String size = scanner.nextLine();
        System.out.println("Enter serial number:");
        String serialNumber = scanner.nextLine();
        System.out.println("Enter year:");
        int year = scanner.nextInt();
        System.out.println("Enter manufacturer:");
        String manufacture = scanner.nextLine();
        System.out.println("Enter equipment status:");
        String equipStatus = scanner.nextLine();

        Equipment newEquipment = new Equipment(equipType, orderId, weight, size,
                serialNumber, year, manufacture, equipStatus);
        this.equipment.add(newEquipment);
    }

    public void printAllEquipment() {
        if (this.equipment.isEmpty()) {
            System.out.println("No equipment to display");
        } else {
            System.out.println("Equipment List: ");
            for (Equipment equip : this.equipment) {
                System.out.println(equip);
            }
        }
    }

    public void deleteEquipmentBySerialNumber(Scanner scanner) {
        boolean quit = false;
        while (!quit) {
            System.out.println(
                    "Enter serial number of equipment to delete (q to quit):");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                quit = true;
            } else {
                Equipment toRemove = null;
                for (Equipment equip : this.equipment) {
                    if (equip.serialNumber.equals(input)) {
                        toRemove = equip;
                        break;
                    }
                }
                if (toRemove == null) {
                    System.out.println("Equipment not found");
                } else {
                    this.equipment.remove(toRemove);
                    System.out.println("Equipment with serial number " + input
                            + " removed");
                }
            }
        }
    }

}