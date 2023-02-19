import java.util.Scanner;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class DatabaseInterface {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private DatabaseInterface() {
    }

    /**
     * Given an array of string options displays them to the user and then
     * repeatedly prompts user for valid selection returning integer
     * corresponding to index + 1 of selected string
     *
     * @param options
     *            Array of prompts to be shown to user
     *
     *
     */
    private static int getSelection(String[] prompts, Scanner in) {
        int input = Integer.MAX_VALUE;

        int i = 1;
        for (String str : prompts) {
            System.out.println("\t" + i + ". " + str);
            i++;
        }
        //selection must be of the prompts
        while (input > prompts.length || input < 1) {
            System.out.print("Input a number 1-" + prompts.length
                    + " to be your selection:");
            String userInput = in.nextLine();
            try {
                input = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                //Input invalid the loop will run again
            }
            if (input > prompts.length || input < 1) {
                System.out.println("Your input couldn't be understood.");
            }
        }
        return input;
    }

    /**
     * Shows the MainMenu
     */
    private static void mainMenu(Scanner in, Database db) {

        System.out.println("How can we help today: ");
        String[] prompts = { "Edit a specific table.",
                "Access useful reports(not implemented)", "Exit" };
        while (true) {
            int choice = getSelection(prompts, in);
            switch (choice) {
                case 1:
                    tableSelector(in, db);
                    break;
                case 2:
                    //Just exits for now
                default:
                    //the program ends
                    System.out.println("Goodbye!");
                    return;
            }
        }

    }

    /**
     * Prompts user to select a table to edit
     *
     * @param in
     */
    private static void tableSelector(Scanner in, Database db) {
        String[] prompts = { "Member", "Equipment", "Warehouse", "Go Back" };
        System.out
                .println("Choose a table to (add/view/delete) records from: ");
        while (true) {
            int choice = getSelection(prompts, in);
            switch (choice) {
                case 1:
                    memberMenu(in, db);
                    break;
                case 2:
                    equipmentMenu(in, db);
                    break;
                case 3:
                    warehouseMenu(in, db);
                    break;
                case 4:
                    return;
                default:
                    break;
            }
        }

    }

    /**
     * Repeatedly prompts user with options to edit Member Table
     *
     * @param in
     */
    private static void memberMenu(Scanner in, Database db) {
        //TODO db class that has members as an instance variable

        MemberTable members = db.getMemberTable();
        String[] prompts = { "Add Member Record", "Remove Member Record",
                "View Records", "Go Back" };

        while (true) {
            System.out.println(
                    "What would you like to do with the Member table: ");
            int choice = getSelection(prompts, in);
            switch (choice) {
                case 1:
                    members.createMember(in);
                    break;
                case 2:
                    members.printAllMembers();
                    members.deleteMembersByInput(in);
                    break;
                case 3:
                    members.printAllMembers();
                    break;
                case 4:
                    return;
                default:
                    //impossible by getSelection contract

            }
        }
    }

    /**
     * Repeatedly prompts user with options to change warehouse table
     *
     * @param in
     */
    private static void warehouseMenu(Scanner in, Database db) {
        //TODO db class that has members as an instance variable

        WarehouseTable warehouses = db.getWarehouseTable();
        String[] prompts = { "Add Warehouse Record", "Remove Warehouse Record",
                "View Records", "Go Back" };

        while (true) {
            System.out.println(
                    "What would you like to do with the Warehouse table: ");
            int choice = getSelection(prompts, in);
            switch (choice) {
                case 1:
                    warehouses.createWarehouse(in);
                    break;
                case 2:
                    warehouses.printAllWarehouses();
                    warehouses.deleteWarehouseByAddressInput(in);
                    break;
                case 3:
                    warehouses.printAllWarehouses();
                    break;
                case 4:
                    return;
                default:
                    //impossible by getSelection contract

            }
        }
    }

    /**
     * /** Repeatedly prompts user with options to change warehouse table
     *
     * @param in
     */
    private static void equipmentMenu(Scanner in, Database db) {
        //TODO db class that has members as an instance variable

        EquipmentTable equipment = db.getEquipmentTable();
        String[] prompts = { "Add Equipment Record", "Remove Equipment Record",
                "View Records", "Go Back" };

        while (true) {
            System.out.println(
                    "What would you like to do with the Equipment table: ");
            int choice = getSelection(prompts, in);
            switch (choice) {
                case 1:
                    equipment.addEquipment(in);
                    break;
                case 2:
                    equipment.printAllEquipment();
                    equipment.deleteEquipmentBySerialNumber(in);
                    break;
                case 3:
                    equipment.printAllEquipment();
                    break;
                case 4:
                    return;
                default:
                    //impossible by getSelection contract

            }
        }
    }

    /**
     * Main method runs the menu until the user is done
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Database db = new Database();
        System.out.println("Welcome to our Database Management Program");
        mainMenu(in, db);

        in.close();
    }

}
