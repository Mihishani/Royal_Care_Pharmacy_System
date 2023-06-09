package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.dao.util.DBUtil;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.CustomerDAO;
import lk.ijse.royal_care_pharmacy.dto.CustomerDTO;
import lk.ijse.royal_care_pharmacy.entity.Customer;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private final Connection connection;

    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static boolean addCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException {
       /* Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=((Connection)connection).prepareStatement("Insert into Customer values (?,?,?,?,?)");
        preparedStatement.setObject(1,customer.getId());
        preparedStatement.setObject(2,customer.getName());
        preparedStatement.setObject(1,customer.getAge());
        preparedStatement.setObject(1,customer.getAddress());
        preparedStatement.setObject(1,customer.getTelNumber());
        int result=preparedStatement.executeUpdate();
        return result>0;*/
        return Boolean.parseBoolean(null);
    }

    public static boolean deleteCustomer(String CusId) throws SQLException, ClassNotFoundException {
        //return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Customer where id='"+id+"'")>0;
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Customer where CusId='" + CusId + "'") > 0;
    }


    public static CustomerDTO search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Customer WHERE CusId = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if (result.next()) {
            return new CustomerDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5)
            );
        }
        return null;
    }

    public static boolean updateCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer set CusName=?,CusAge=?,CusAddress=?,CusTelNumber=? where CusId=?");
        preparedStatement.setObject(1, customer.getCusName());
        preparedStatement.setObject(2, customer.getCusAge());
        preparedStatement.setObject(3, customer.getCusAddress());
        preparedStatement.setObject(4, customer.getCusTelNumber());
        preparedStatement.setObject(5, customer.getCusId());
        return preparedStatement.executeUpdate() > 0;
    }


    @Override
    public lk.ijse.royal_care_pharmacy.entity.Customer save(lk.ijse.royal_care_pharmacy.entity.Customer customer) throws ViolationException, SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.Customer VALUES (?, ?, ?, ?, ?)";
        boolean b = DBUtil.executeUpdate(sql, customer.getCusId(), customer.getCusName(), customer.getCusAge(), customer.getCusAddress(), customer.getCusTelNumber());
        return b ? new Customer() : null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Customer update(lk.ijse.royal_care_pharmacy.entity.Customer customer) throws ViolationException, ClassNotFoundException, SQLException {
        return CrudUtil.execute("UPDATE royal_care_pharmacy.customer set CusName=?,CusAge=?,CusAddress=?,CusTelNumber=? where CusId=?",customer.getCusName(),customer.getCusAge(),customer.getCusAddress(),customer.getCusTelNumber(),customer.getCusId());
    }

    @Override
    public boolean deleteByPk(String CusId) throws ViolationException, SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Customer where CusId='" + CusId + "'") > 0;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Customer> findAll() {

        return null;
    }

    @Override
    public Customer findByPk(String cusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Customer WHERE CusId = ?";
        ResultSet result = CrudUtil.execute(sql, cusId);

        if (result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5)
            );
        }
        return null;
    }

    @Override
    public boolean existByPk(String cusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM royal_care_pharmacy.customer WHERE CusId=?";
        ResultSet resultSet = CrudUtil.execute(sql, cusId);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }
}

