package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.EmployeeDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService extends SuperService {
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public  EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException;

    public EmployeeDTO DeleteEmployee(String EID) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<EmployeeDTO> findAllEmployees();

    public EmployeeDTO findEmployeeByCusId (String EID) throws SQLException, NotFoundException, ClassNotFoundException;
}
