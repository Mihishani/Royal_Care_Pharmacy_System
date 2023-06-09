package lk.ijse.royal_care_pharmacy.entity;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;

public class Employee extends CrudDAO.SuperEntity implements SuperEntity{
    private String EId;
    private String EName;
    private String EAddress;
    private String Email;
    private int ETelNumber;
    private String EPassword;
    private String User_Name;

    public Employee() {
    }

    public Employee(String EId, String EName, String EAddress, String email, int ETelNumber, String EPassword, String user_Name) {
        this.EId = EId;
        this.EName = EName;
        this.EAddress = EAddress;
        Email = email;
        this.ETelNumber = ETelNumber;
        this.EPassword = EPassword;
        User_Name = user_Name;
    }

    public String getEId() {
        return EId;
    }

    public void setEId(String EId) {
        this.EId = EId;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public String getEAddress() {
        return EAddress;
    }

    public void setEAddress(String EAddress) {
        this.EAddress = EAddress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getETelNumber() {
        return ETelNumber;
    }

    public void setETelNumber(int ETelNumber) {
        this.ETelNumber = ETelNumber;
    }

    public String getEPassword() {
        return EPassword;
    }

    public void setEPassword(String EPassword) {
        this.EPassword = EPassword;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "EId='" + EId + '\'' +
                ", EName='" + EName + '\'' +
                ", EAddress='" + EAddress + '\'' +
                ", Email='" + Email + '\'' +
                ", ETelNumber=" + ETelNumber +
                ", EPassword='" + EPassword + '\'' +
                ", User_Name='" + User_Name + '\'' +
                '}';
    }
}
