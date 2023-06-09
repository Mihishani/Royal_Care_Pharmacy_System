package lk.ijse.royal_care_pharmacy.dao.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    boolean existByPk(String cusId) throws SQLException, ClassNotFoundException;
}
