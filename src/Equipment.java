
public class Equipment {
    public String equipType;
    public int orderId;
    public double weight;
    public String size;
    //Serial number can start with 0 so it shouldn't be an int
    public String serialNumber;
    public int year;
    public String manufacture;
    public String equipStatus;

    public Equipment(String equipType, int orderId, double weight, String size,
            String serialNumber, int year, String manufacture,
            String equipStatus) {
        this.equipType = equipType;
        this.orderId = orderId;
        this.weight = weight;
        this.size = size;
        this.serialNumber = serialNumber;
        this.year = year;
        this.manufacture = manufacture;
        this.equipStatus = equipStatus;
    }

    @Override
    public String toString() {
        return "Equipment{" + "equipType='" + this.equipType + '\''
                + ", orderId=" + this.orderId + ", weight=" + this.weight
                + ", size='" + this.size + '\'' + ", serialNumber='"
                + this.serialNumber + '\'' + ", year=" + this.year
                + ", manufacture='" + this.manufacture + '\''
                + ", equipStatus='" + this.equipStatus + '\'' + '}';
    }
}
