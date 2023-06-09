package lk.ijse.royal_care_pharmacy.dto;

public class AttendenceDTO {
    private String NIC;
    private String EID;
    private String EName;
    private String ADate;
    private String Description;

    public AttendenceDTO(){
    }

    public AttendenceDTO(String NIC, String EID, String EName, String ADate, String description) {
        this.NIC = NIC;
        this.EID = EID;
        this.EName = EName;
        this.ADate = ADate;
        Description = description;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
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

    public String getADate() {
        return ADate;
    }

    public void setADate(String ADate) {
        this.ADate = ADate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Attendence{" +
                "NIC='" + NIC + '\'' +
                ", EID='" + EID + '\'' +
                ", EName='" + EName + '\'' +
                ", ADate='" + ADate + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
