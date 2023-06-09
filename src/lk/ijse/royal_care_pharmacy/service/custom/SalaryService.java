package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.SalaryDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface SalaryService extends SuperService {
    public SalaryDTO saveSalary(SalaryDTO salaryDTO) throws DuplicateException, CrudDAO.ViolationException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public SalaryDTO updateSalary(SalaryDTO salaryDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException;

    public void DeleteSalary(String SalId) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<SalaryDTO> findAllSalary();

    public SalaryDTO findSalaryBySalId (String SalId) throws SQLException, NotFoundException, ClassNotFoundException;
}
