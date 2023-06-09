package lk.ijse.royal_care_pharmacy.dto;

public class OrderDTO {
     private String OID;
     private String CusId;
     private String ODate;

    public OrderDTO() {
    }

    public OrderDTO(String OID, String cusId, String ODate) {
        this.OID = OID;
        CusId = cusId;
        this.ODate = ODate;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getCusId() {
        return CusId;
    }

    public void setCusId(String cusId) {
        CusId = cusId;
    }

    public String getODate() {
        return ODate;
    }

    public void setODate(String ODate) {
        this.ODate = ODate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OID='" + OID + '\'' +
                ", CusId='" + CusId + '\'' +
                ", ODate='" + ODate + '\'' +
                '}';
    }
}
