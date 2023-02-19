import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Ben Ware
 *
 */
public class MemberTable {

    private HashSet<Member> members;

    public MemberTable() {
        this.members = new HashSet<Member>();
    }

    public void createMember(Scanner scanner) {

        // Prompt the user to enter the instance fields of a Member object
        System.out.println("Enter the member details:");
        System.out.print("ID: ");
        int id = 0;
        while (true) {
            try {
                id = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid ID. Please enter an integer: ");
            }
        }
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Start date (YYYY-MM-DD): ");
        Date startDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            try {
                startDate = sdf.parse(scanner.nextLine());
                break;
            } catch (ParseException e) {
                System.out.print(
                        "Invalid date format. Please enter a date in the format YYYY-MM-DD: ");
            }
        }
        System.out.print("Status ID: ");
        int statusId = 0;
        while (true) {
            try {
                statusId = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out
                        .print("Invalid status ID. Please enter an integer: ");
            }
        }

        Member mem = new Member(id, firstName, lastName, address, phoneNumber,
                email, startDate, statusId);
        System.out.println("New member created: " + mem.toString());
        // Create a new Member object with the input values
        this.members.add(mem);

    }

    public void deleteMember(int id) {
        this.members.removeIf(member -> member.id == id);
    }

    public void printAllMembers() {
        if (this.members.isEmpty()) {
            System.out.println("No members to display.");
        } else {
            System.out.println("Member List:");
            for (Member member : this.members) {
                System.out.println(member.toString());
            }
        }
    }

    public void deleteMembersByInput(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Enter the ID of the member to delete (or 'q' to quit): ");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                break;
            }
            try {
                int id = Integer.parseInt(input);
                this.deleteMember(id);
                System.out.println("Member with ID " + id + " deleted.");
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter an integer ID or 'q' to quit.");
            }
        }
    }
}
