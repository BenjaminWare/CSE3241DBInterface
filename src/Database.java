public class Database {
    private MemberTable memberTable;
    private EquipmentTable equipmentTable;
    private WarehouseTable warehouseTable;

    public Database() {
        this.memberTable = new MemberTable();
        this.equipmentTable = new EquipmentTable();
        this.warehouseTable = new WarehouseTable();
    }

    public MemberTable getMemberTable() {
        return this.memberTable;
    }

    public void setMemberTable(MemberTable memberTable) {
        this.memberTable = memberTable;
    }

    public EquipmentTable getEquipmentTable() {
        return this.equipmentTable;
    }

    public void setEquipmentTable(EquipmentTable equipmentTable) {
        this.equipmentTable = equipmentTable;
    }

    public WarehouseTable getWarehouseTable() {
        return this.warehouseTable;
    }

    public void setWarehouseTable(WarehouseTable warehouseTable) {
        this.warehouseTable = warehouseTable;
    }
}