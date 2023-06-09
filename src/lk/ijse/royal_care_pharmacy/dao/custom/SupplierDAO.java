package lk.ijse.royal_care_pharmacy.dao.custom;


import lk.ijse.royal_care_pharmacy.dto.SupplierDTO;
import lk.ijse.royal_care_pharmacy.entity.Supplier;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;

public interface SupplierDAO extends CrudDAO<Supplier,String> {

    boolean existByPk(String supId);
}
