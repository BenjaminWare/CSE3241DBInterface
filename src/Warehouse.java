public class Warehouse {
    public String city;
    public String address;
    public String phoneNumber;
    public String managerName;
    public int storageCapacity;
    public int droneCapacity;

    public Warehouse(String city, String address, String phoneNumber,
            String managerName, int storageCapacity, int droneCapacity) {
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.managerName = managerName;
        this.storageCapacity = storageCapacity;
        this.droneCapacity = droneCapacity;
    }

    @Override
    public String toString() {
        return "Warehouse{" + "city='" + this.city + '\'' + ", address='"
                + this.address + '\'' + ", phoneNumber='" + this.phoneNumber
                + '\'' + ", managerName='" + this.managerName + '\''
                + ", storageCapacity=" + this.storageCapacity
                + ", droneCapacity=" + this.droneCapacity + '}';
    }
}