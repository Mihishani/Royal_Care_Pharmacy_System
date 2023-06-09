package lk.ijse.royal_care_pharmacy.view.tm;

import javafx.scene.control.Button;

public class PlaceOrderTm {
    private String ItemId;
    private String ItemName;
    private String Description;
    private int QTY;
    private double Price;
    private double total;
    private Button btnDelete;

    public PlaceOrderTm(){

    }

    public PlaceOrderTm(String itemId, String itemName, String description, int QTY, double price, double total, Button btnDelete) {
        ItemId = itemId;
        ItemName = itemName;
        Description = description;
        this.QTY = QTY;
        Price = price;
        this.total = total;
        this.btnDelete = btnDelete;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "PlaceOrderTm{" +
                "ItemId='" + ItemId + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", Description='" + Description + '\'' +
                ", QTY=" + QTY +
                ", Price=" + Price +
                ", total=" + total +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
