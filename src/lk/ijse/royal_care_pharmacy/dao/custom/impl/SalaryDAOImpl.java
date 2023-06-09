package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.SalaryDAO;
import lk.ijse.royal_care_pharmacy.dto.SalaryDTO;
import lk.ijse.royal_care_pharmacy.entity.Salary;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    private final Connection connection;

    public SalaryDAOImpl(Connection connection) {
        this.connection=connection;
    }

    public static boolean addSalary(SalaryDTO salary) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.salary VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,salary.getEID(), salary.getEName(),salary.getSalId(),salary.getAmount(),salary.getPMonth(),salary.getPaidOrNot());
    }

    public static boolean updateSalary(SalaryDTO salary) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Salary set EID=?,EName=?,Amount=?,PMonth=?,PaidOrNot=? where SalId=?");
        preparedStatement.setObject(1,salary.getEID());
        preparedStatement.setObject(2,salary.getEName());
        preparedStatement.setObject(3,salary.getAmount());
        preparedStatement.setObject(4,salary.getPMonth());
        preparedStatement.setObject(5,salary.getPaidOrNot());
        preparedStatement.setObject(6,salary.getSalId());
        return preparedStatement.executeUpdate()>0;
    }

    public static boolean deleteSalary(String salId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Salary where SalId='"+salId+"'")>0;
    }

    public static SalaryDTO search(String salId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Salary WHERE SalId = ?";
        ResultSet result = CrudUtil.execute(sql, salId);

        if (result.next()) {
            return new SalaryDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        return null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Salary save(lk.ijse.royal_care_pharmacy.entity.Salary entity) throws ViolationException {
        return null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Salary update(lk.ijse.royal_care_pharmacy.entity.Salary entity) throws ViolationException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteByPk(String pk) throws ViolationException {

        return false;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Salary> findAll() {
        return null;
    }

    @Override
    public Salary findByPk(String pk) {
        return null;
    }

    @Override
    public boolean existByPk(String salId) {
        return false;
    }

    /*@Override
    public boolean existByPk(String pk) {
        return false;
    }*/


}
