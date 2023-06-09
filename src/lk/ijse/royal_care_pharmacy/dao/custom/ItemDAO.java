package lk.ijse.royal_care_pharmacy.dao.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean existByPk(String itemId) throws SQLException, ClassNotFoundException;
}
