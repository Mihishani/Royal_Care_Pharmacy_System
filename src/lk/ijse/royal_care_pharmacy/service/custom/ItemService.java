package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.CustomerDTO;
import lk.ijse.royal_care_pharmacy.dto.ItemDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ItemService extends SuperService {
    public ItemDTO saveItem(ItemDTO itemDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public ItemDTO updateItem(ItemDTO itemDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException;

    public void DeleteItem(String ItemId) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<ItemDTO> findAllItem();

    public ItemDTO findItemByItemId (String ItemId) throws SQLException, NotFoundException, ClassNotFoundException;
}
