package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.CustomerDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService extends SuperService {
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException;

    public boolean DeleteCustomer(String CusId) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<CustomerDTO> findAllCustomer();

    public  CustomerDTO findCustomerByCusId (String CusId) throws SQLException, NotFoundException, ClassNotFoundException;
}
