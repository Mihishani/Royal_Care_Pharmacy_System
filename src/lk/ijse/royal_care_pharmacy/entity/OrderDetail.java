package lk.ijse.royal_care_pharmacy.entity;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;

public class OrderDetail extends CrudDAO.SuperEntity implements SuperEntity{
    private String OID;
    private String ItemId;
    private String Price;
    private String Qty;

    public OrderDetail() {
    }

    public OrderDetail(String OID, String itemId, String price, String qty) {
        this.OID = OID;
        ItemId = itemId;
        Price = price;
        Qty = qty;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "OID='" + OID + '\'' +
                ", ItemId='" + ItemId + '\'' +
                ", Price='" + Price + '\'' +
                ", Qty='" + Qty + '\'' +
                '}';
    }
}
