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

            //Creates menus
            WarehouseMenu wm = new WarehouseMenu(conn);
            MemberMenu mm = new MemberMenu(conn);
            InventoryMenu im = new InventoryMenu(conn);
            im.run();
        } catch (SQLException e) {
            System.out.println("Error connectiong to SQLite DB");
            e.printStackTrace();
        }
        in.close();
    }

}
