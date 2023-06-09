package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.AttendenceDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AttendenceService extends SuperService {

    public AttendenceDTO saveAttendence(AttendenceDTO attendenceDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException;

    public AttendenceDTO updateAttendence(AttendenceDTO attendenceDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException;

    public void DeleteAttendence(String NIC) throws NotFoundException, SQLException, ClassNotFoundException;

    public List<AttendenceDTO> findAllAttendence();

    public AttendenceDTO findAttendenceByNic (String NIC) throws NotFoundException, SQLException, ClassNotFoundException;


}
