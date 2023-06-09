package lk.ijse.royal_care_pharmacy.service;

import lk.ijse.royal_care_pharmacy.service.custom.impl.*;

import java.sql.SQLException;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return serviceFactory == null ? (serviceFactory = new ServiceFactory()) : serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceTypes) throws SQLException, ClassNotFoundException {
        switch (serviceTypes) {
            case ATTENDENCE:
                return (T) new AttendenceServiceImpl();

            case CUSTOMER:
                return (T) new CustomerServiceImpl();

            case STOCK:
                return (T) new BatchServiceImpl();

            case EMPLOYEE:
                return (T) new EmployeeServiceImpl();

            case ITEM:
                return (T) new ItemServiceImpl();

            case ORDER:
                return (T) new OrderServiceImpl();

            case ORDERDETAIL:
                return (T) new OrderDetailServiceImpl();

            case SALARY:
                return (T) new SalaryServiceImpl();

            case SUPPLIER:
                return (T) new SupplierServiceImpl();

            default:
                return null;
        }
    }
}
