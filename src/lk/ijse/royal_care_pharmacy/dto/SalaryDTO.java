package lk.ijse.royal_care_pharmacy.dto;

public class SalaryDTO {
    private String EID;
    private String EName;
    private String SalId;
    private double Amount;
    private String PMonth;
    private String PaidOrNot;

    public SalaryDTO(){

    }

    public SalaryDTO(String EID, String EName, String salId, double amount, String PMonth, String paidOrNot) {
        this.EID = EID;
        this.EName = EName;
        SalId = salId;
        Amount = amount;
        this.PMonth = PMonth;
        PaidOrNot = paidOrNot;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public String getSalId() {
        return SalId;
    }

    public void setSalId(String salId) {
        SalId = salId;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getPMonth() {
        return PMonth;
    }

    public void setPMonth(String PMonth) {
        this.PMonth = PMonth;
    }

    public String getPaidOrNot() {
        return PaidOrNot;
    }

    public void setPaidOrNot(String paidOrNot) {
        PaidOrNot = paidOrNot;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "EID='" + EID + '\'' +
                ", EName='" + EName + '\'' +
                ", SalId='" + SalId + '\'' +
                ", Amount=" + Amount +
                ", PMonth='" + PMonth + '\'' +
                ", PaidOrNot='" + PaidOrNot + '\'' +
                '}';
    }
}
