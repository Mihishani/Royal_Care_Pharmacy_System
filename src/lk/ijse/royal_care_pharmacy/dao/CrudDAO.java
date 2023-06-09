package lk.ijse.royal_care_pharmacy.dao;




import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T extends CrudDAO.SuperEntity,ID extends Serializable> extends SuperDAO {

    T save(T entity) throws ViolationException, SQLException, ClassNotFoundException;

    T update(T entity) throws ViolationException, ClassNotFoundException, SQLException;

    boolean deleteByPk(ID pk) throws ViolationException, SQLException, ClassNotFoundException;

    List<T> findAll() ;

     T findByPk(ID pk) throws SQLException, ClassNotFoundException;

    //boolean existByPk(ID pk) throws SQLException, ClassNotFoundException;


    class ViolationException extends Exception {
        public ViolationException(Exception e) {
        }
    }

    class SuperEntity {
    }
}
