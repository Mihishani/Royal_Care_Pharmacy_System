package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.SupplierDAO;
import lk.ijse.royal_care_pharmacy.dto.SupplierDTO;
import lk.ijse.royal_care_pharmacy.entity.Supplier;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    private final Connection connection;

    public SupplierDAOImpl(Connection connection) {
        this.connection=connection;
    }

    public static boolean addSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.Supplier VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, supplier.getSupId(), supplier.getSName(),supplier.getAddress(),supplier.getEmail(),supplier.getTelNumber());
    }

    public static boolean deleteSupplier(String supId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Supplier where SupId='"+supId+"'")>0;
    }

    public static boolean updateSupplier(SupplierDTO supplier) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Supplier set SName=?,Address=?,Email=?,TelNumber=? where SupId=?");
        preparedStatement.setObject(1,supplier.getSName());
        preparedStatement.setObject(2,supplier.getAddress());
        preparedStatement.setObject(3,supplier.getEmail());
        preparedStatement.setObject(4,supplier.getTelNumber());
        preparedStatement.setObject(5,supplier.getSupId());
        return preparedStatement.executeUpdate()>0;
    }

    public static SupplierDTO search(String supId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Supplier WHERE SupId = ?";
        ResultSet result = CrudUtil.execute(sql, supId);

        if (result.next()) {
            return new SupplierDTO(
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
    public lk.ijse.royal_care_pharmacy.entity.Supplier save(lk.ijse.royal_care_pharmacy.entity.Supplier entity) throws ViolationException {
        return null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Supplier update(lk.ijse.royal_care_pharmacy.entity.Supplier entity) throws ViolationException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteByPk(String pk) throws ViolationException {

        return false;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Supplier> findAll() {
        return null;
    }

    @Override
    public Supplier findByPk(String pk) {
        return null;
    }

    @Override
    public boolean existByPk(String pk) {
        return false;
    }

}
