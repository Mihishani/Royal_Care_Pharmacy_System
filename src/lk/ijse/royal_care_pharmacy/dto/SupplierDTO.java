package lk.ijse.royal_care_pharmacy.dto;

public class SupplierDTO {
    private String SupId;
    private String SName;
    private String Address;
    private String Email;
    private int TelNumber;

    public SupplierDTO(){

    }

    public SupplierDTO(String supId, String SName, String address, String email, int telNumber) {
        SupId = supId;
        this.SName = SName;
        Address = address;
        Email = email;
        TelNumber = telNumber;
    }

    public String getSupId() {
        return SupId;
    }

    public void setSupId(String supId) {
        SupId = supId;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getTelNumber() {
        return TelNumber;
    }

    public void setTelNumber(int telNumber) {
        TelNumber = telNumber;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "SupId='" + SupId + '\'' +
                ", SName='" + SName + '\'' +
                ", Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                ", TelNumber=" + TelNumber +
                '}';
    }
}
