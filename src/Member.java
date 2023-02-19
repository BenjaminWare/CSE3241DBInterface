import java.util.Date;

/**
 * Member record
 *
 * @author Ben Ware
 *
 */
public class Member {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String phoneNumber;
    public String email;
    public Date startDate;
    public int statusId;

    public Member(int id, String firstName, String lastName, String address,
            String phoneNumber, String email, Date startDate, int statusId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.startDate = startDate;
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Member {" + "id=" + this.id + ", firstName='" + this.firstName
                + '\'' + ", lastName='" + this.lastName + '\'' + ", address='"
                + this.address + '\'' + ", phoneNumber='" + this.phoneNumber
                + '\'' + ", email='" + this.email + '\'' + ", startDate="
                + this.startDate + ", statusId=" + this.statusId + '}';
    }
}
