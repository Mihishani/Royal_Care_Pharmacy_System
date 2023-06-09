package lk.ijse.royal_care_pharmacy.dao.custom;


import lk.ijse.royal_care_pharmacy.entity.Salary;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;

public interface SalaryDAO extends CrudDAO<Salary,String> {
    boolean existByPk(String salId);
}
