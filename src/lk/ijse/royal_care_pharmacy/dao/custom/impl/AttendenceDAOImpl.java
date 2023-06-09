package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.AttendenceDAO;
import lk.ijse.royal_care_pharmacy.dto.AttendenceDTO;
import lk.ijse.royal_care_pharmacy.entity.Attendence;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendenceDAOImpl implements AttendenceDAO {

    private final Connection connection;

    public AttendenceDAOImpl(Connection connection) {
            this.connection=connection;
    }
    /*public static Attendence  getAttendence(String NIC) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM Attendence where NIC = ?",NIC);
        if (resultSet.next()){
            return new Attendence(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }*/

    public static ArrayList<String> loadEmpIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT EID FROM royal_care_pharmacy.Employee";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static boolean addAttendence(AttendenceDTO attendence) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.attendence VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,attendence.getNIC(),attendence.getEID(),attendence.getEName(),attendence.getADate(),attendence.getDescription());

    }


    public static AttendenceDTO search(String eid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM  royal_care_pharmacy.attendence WHERE NIC = ?";
        ResultSet result = CrudUtil.execute(sql,eid);

        if (result.next()) {
            return new AttendenceDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)

            );
        }
        return null;
    }

    public static boolean updateAttendence(AttendenceDTO attendence) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Attendence set EID=?,EName=?,ADate=?,Description=? where NIC=?");
        preparedStatement.setObject(1,attendence.getEID());
        preparedStatement.setObject(2,attendence.getEName());
        preparedStatement.setObject(3,attendence.getADate());
        preparedStatement.setObject(4,attendence.getDescription());
        preparedStatement.setObject(5,attendence.getNIC());
        return preparedStatement.executeUpdate()>0;
    }


    public static boolean deleteAttendence(String nic) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Attendence where NIC='"+nic+"'")>0;
    }


    public static AttendenceDTO searchAttendence(String nic) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM  royal_care_pharmacy.attendence WHERE NIC = ?";
        ResultSet result = CrudUtil.execute(sql,nic);

        if (result.next()) {
            return new AttendenceDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)

            );
        }
        return null;
    }


    @Override
    public lk.ijse.royal_care_pharmacy.entity.Attendence save(lk.ijse.royal_care_pharmacy.entity.Attendence entity) throws ViolationException {
        return null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Attendence update(lk.ijse.royal_care_pharmacy.entity.Attendence a) throws ViolationException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteByPk(String pk) throws ViolationException {

        return false;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Attendence> findAll() {
        return null;
    }

    @Override
    public Attendence findByPk(String pk) {
        return null;
    }

    @Override
    public boolean existByPk(String pk) {
        return false;
    }


}
