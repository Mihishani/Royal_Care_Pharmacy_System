package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.dao.util.DBUtil;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.EmployeeDAO;
import lk.ijse.royal_care_pharmacy.dto.EmployeeDTO;
import lk.ijse.royal_care_pharmacy.entity.Customer;
import lk.ijse.royal_care_pharmacy.entity.Employee;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection=connection;
    }

    public static boolean addEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
       return Boolean.parseBoolean(null);
    }

    public static boolean updateEmployee(EmployeeDTO employee) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Employee set EName=?,EAddress=?,Email=?,ETelNumber=?,EPassword=?,User_Name=? where EId=?");
        preparedStatement.setObject(1,employee.getEName());
        preparedStatement.setObject(2,employee.getEAddress());
        preparedStatement.setObject(3,employee.getEmail());
        preparedStatement.setObject(4,employee.getETelNumber());
        preparedStatement.setObject(5,employee.getEPassword());
        preparedStatement.setObject(6,employee.getUser_Name());
        preparedStatement.setObject(7,employee.getEId());
        return preparedStatement.executeUpdate()>0;
    }

    public static EmployeeDTO search(String eId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Employee WHERE EId = ?";
        ResultSet result = CrudUtil.execute(sql,eId);

        if (result.next()) {
            return new EmployeeDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getString(6),
                    result.getString(7)
            );
        }
        return null;
    }


    public static boolean DeleteEmployee(String eId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Employee where EId='"+eId+"'")>0;
    }


    @Override
    public lk.ijse.royal_care_pharmacy.entity.Employee save(lk.ijse.royal_care_pharmacy.entity.Employee employee) throws ViolationException, SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.Employee VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean isAdd= DBUtil.executeUpdate(sql, employee.getEId(), employee.getEName(), employee.getEAddress(), employee.getEmail(), employee.getETelNumber(), employee.getEPassword(), employee.getUser_Name());
        return isAdd ? new Employee() : null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Employee update(lk.ijse.royal_care_pharmacy.entity.Employee employee) throws ViolationException, ClassNotFoundException, SQLException {
        return CrudUtil.execute("UPDATE royal_care_pharmacy.employee set EName=?,EAddress=?,Email=?,ETelNumber=?,EPassword=?,User_Name=? where EId=?",employee.getEName(),employee.getEAddress(),employee.getEmail(),employee.getETelNumber(),employee.getEPassword(),employee.getUser_Name(),employee.getEId());
    }

    @Override
    public boolean deleteByPk(String eId) throws ViolationException, SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Employee where EId='"+eId+"'")>0;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Employee> findAll() {
        return null;
    }

    @Override
    public Employee findByPk(String eId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Employee WHERE EId = ?";
        ResultSet result = CrudUtil.execute(sql,eId);

        if (result.next()) {
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getString(6),
                    result.getString(7)
            );
        }
        return null;
    }

    @Override
    public boolean existByPk(String eid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM royal_care_pharmacy.employee WHERE EID=?";
        ResultSet resultSet = CrudUtil.execute(sql, eid);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }


}
