package lk.ijse.royal_care_pharmacy.entity;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;

public class Item extends CrudDAO.SuperEntity implements SuperEntity{
    private String ItemId;
    private String Item_Name;
    private double Price;
    private String MadeOfDate;
    private String ExpireOfDate;
    private int QTY;
    private String Description;

    public Item() {
    }

    public Item(String itemId, String item_Name, double price, String madeOfDate, String expireOfDate, int QTY, String description) {
        ItemId = itemId;
        Item_Name = item_Name;
        Price = price;
        MadeOfDate = madeOfDate;
        ExpireOfDate = expireOfDate;
        this.QTY = QTY;
        Description = description;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getMadeOfDate() {
        return MadeOfDate;
    }

    public void setMadeOfDate(String madeOfDate) {
        MadeOfDate = madeOfDate;
    }

    public String getExpireOfDate() {
        return ExpireOfDate;
    }

    public void setExpireOfDate(String expireOfDate) {
        ExpireOfDate = expireOfDate;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ItemId='" + ItemId + '\'' +
                ", Item_Name='" + Item_Name + '\'' +
                ", Price=" + Price +
                ", MadeOfDate='" + MadeOfDate + '\'' +
                ", ExpireOfDate='" + ExpireOfDate + '\'' +
                ", QTY=" + QTY +
                ", Description='" + Description + '\'' +
                '}';
    }
}
