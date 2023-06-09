package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.SupplierDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService extends SuperService {
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) throws DuplicateException, CrudDAO.ViolationException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public SupplierDTO updateSupplier(SupplierDTO supplierDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException;

    public void DeleteSupplier(String SupId) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<SupplierDTO> findAllSupplier();

    public SupplierDTO findSupplierBySupId (String SupId) throws SQLException, NotFoundException, ClassNotFoundException;
}
