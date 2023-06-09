package lk.ijse.royal_care_pharmacy.dao;

import lk.ijse.royal_care_pharmacy.dao.custom.impl.*;

import java.sql.Connection;

public class DaoFactory {
    private static DaoFactory daoFactory ;
    private DaoFactory() {
    }

    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory=new DaoFactory()):daoFactory;
    }

    public <T extends SuperDAO> T getDAO(Connection connection, DaoTypes daoType) {
        switch (daoType) {
            case ATTENDENCE:
                return (T) new AttendenceDAOImpl(connection);

            case STOCK:
                return (T) new BatchDAOImpl(connection);

            case CUSTOMER:
                return (T) new CustomerDAOImpl(connection);

            case EMPLOYEE:
                return (T) new EmployeeDAOImpl(connection);

            case ITEM:
                return (T) new ItemDAOImpl(connection);

            case ORDER:
                return (T) new OrderDAOImpl(connection);


            case SALARY:
                return (T) new SalaryDAOImpl(connection);

            case SUPPLIER:
                return (T) new SupplierDAOImpl(connection);

            default:
                return null;

        }
    }
}
