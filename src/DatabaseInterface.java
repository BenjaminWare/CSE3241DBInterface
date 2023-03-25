import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    static final String JDBC_DRIVER = "jdbc:sqlite:/";
    //Where the DB is on my development machine
    static String DB_URL = "C:\\Program Files\\SQLiteStudio\\testDB.db";

    public static void tableMenu(Connection conn, Scanner in) {
        while (true) {
            System.out.println("Choose an option by number (q to quit):");
            System.out.println("1. Warehouse Table");
            System.out.println("2. Member Table");
            System.out.println("3. Inventory Table");
            System.out.println("4. Equipment Table");
            System.out.println("5. Drone Table");
            System.out.println("6. Equipment Order Table");
            System.out.println("7. Inventory Repair Shop Table");
            System.out.println("8. Inventory Repair Record Table");
            System.out.println("9. Return Table");
            System.out.println("10. Delivery Table");
            System.out.println("11 Rental Table");
            System.out.println("12. Service Request Review Table");
            System.out.println("13. Equipment Review Table");

            String option = in.nextLine();
            if (option.equals("q")) {
                return;
            }
            switch (option) {
                case "1":
                    WarehouseMenu wm = new WarehouseMenu(conn);
                    wm.run();
                    break;
                case "2":
                    MemberMenu mm = new MemberMenu(conn);
                    mm.run();
                    break;
                case "3":
                    InventoryMenu im = new InventoryMenu(conn);
                    im.run();
                    break;
                case "4":
                    EquipmentMenu em = new EquipmentMenu(conn);
                    em.run();
                    break;
                case "5":
                    DroneMenu dm = new DroneMenu(conn);
                    dm.run();
                    break;
                case "6":
                    EquipmentOrderMenu eom = new EquipmentOrderMenu(conn);
                    eom.run();
                    break;
                case "7":
                    InventoryRepairShopMenu irsm = new InventoryRepairShopMenu(
                            conn);
                    irsm.run();
                    break;
                case "8":
                    InventoryRepairRecordMenu irrm = new InventoryRepairRecordMenu(
                            conn);
                    irrm.run();
                    break;
                case "9":
                    ReturnMenu returnMenu = new ReturnMenu(conn);
                    returnMenu.run();
                    break;
                case "10":
                    DeliveryMenu deliveryMenu = new DeliveryMenu(conn);
                    deliveryMenu.run();
                    break;
                case "11":
                    RentalMenu rentalMenu = new RentalMenu(conn);
                    rentalMenu.run();
                    break;
                case "12":
                    ServiceRequestReviewMenu srrm = new ServiceRequestReviewMenu(
                            conn);
                    srrm.run();
                    break;
                case "13":
                    EquipmentReviewMenu erm = new EquipmentReviewMenu(conn);
                    erm.run();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        }
    }

    public static void mainMenu(Connection conn) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Main Menu");
            System.out.println("Choose an option by number (q to quit):");
            System.out.println("1. Create/Read/Update/Delete from Tables.");
            System.out.println("2. Useful reports.");

            String option = in.nextLine();
            if (option.equals("q")) {
                in.close();
                return;
            }
            switch (option) {
                case "1":
                    tableMenu(conn, in);
                    break;
                case "2":
                    //nothing yet
                    break;
                default:
                    System.out.println("Invalid choice.");
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
        System.out.println("Welcome to our Database Management Program");

        try {
            System.out.println(
                    "Enter the absolute path of the db file (or nothing for default):");
            String db = in.nextLine();
            if (!db.equals("")) {
                DB_URL = db;
            }
            //Connects to DB
            Connection conn = DriverManager.getConnection(JDBC_DRIVER + DB_URL);

            mainMenu(conn);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error connectiong to SQLite DB");
            e.printStackTrace();
        }
        in.close();
    }

}
