package lk.ijse.royal_care_pharmacy.dao.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.entity.Batch;

import java.sql.SQLException;

public interface BatchDAO extends CrudDAO<Batch,String> {
    boolean existByPk(String batchId) throws SQLException, ClassNotFoundException;
}
