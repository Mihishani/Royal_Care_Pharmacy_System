package lk.ijse.royal_care_pharmacy.dao.custom;

import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.entity.Attendence;

public interface AttendenceDAO extends CrudDAO<Attendence,String> {

    boolean existByPk(String nic);
}
