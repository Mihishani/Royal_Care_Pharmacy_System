package lk.ijse.royal_care_pharmacy.dao.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee,String> {
    boolean existByPk(String eId) throws SQLException, ClassNotFoundException;
}
