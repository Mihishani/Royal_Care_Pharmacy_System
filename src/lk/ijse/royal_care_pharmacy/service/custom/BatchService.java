package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.BatchDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface BatchService extends SuperService {
    public BatchDTO saveBatch(BatchDTO batchDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public BatchDTO updateBatch(BatchDTO batchDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException;

    public void DeleteBatch(String BatchId) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<BatchDTO> findAllBatch();

    public BatchDTO findBatchByBatchId (String BatchId) throws SQLException, NotFoundException, ClassNotFoundException;
}
