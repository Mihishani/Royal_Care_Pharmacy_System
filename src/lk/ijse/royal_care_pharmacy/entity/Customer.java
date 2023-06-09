package lk.ijse.royal_care_pharmacy.entity;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import rex.utils.S;

public class Customer extends CrudDAO.SuperEntity implements SuperEntity {
    private String CusId;
    private String CusName;
    private String CusAge;
    private String CusAddress;
    private int CusTelNumber;

    public Customer() {
    }

    public Customer(String cusId, String cusName, String cusAge, String cusAddress, int cusTelNumber) {
        CusId = cusId;
        CusName = cusName;
        CusAge = cusAge;
        CusAddress = cusAddress;
        CusTelNumber = cusTelNumber;
    }

    public String getCusId() {
        return CusId;
    }

    public void setCusId(String cusId) {
        CusId = cusId;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getCusAge() {
        return CusAge;
    }

    public void setCusAge(String cusAge) {
        CusAge = cusAge;
    }

    public String getCusAddress() {
        return CusAddress;
    }

    public void setCusAddress(String cusAddress) {
        CusAddress = cusAddress;
    }

    public int getCusTelNumber() {
        return CusTelNumber;
    }

    public void setCusTelNumber(int cusTelNumber) {
        CusTelNumber = cusTelNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CusId='" + CusId + '\'' +
                ", CusName='" + CusName + '\'' +
                ", CusAge='" + CusAge + '\'' +
                ", CusAddress='" + CusAddress + '\'' +
                ", CusTelNumber=" + CusTelNumber +
                '}';
    }
}
