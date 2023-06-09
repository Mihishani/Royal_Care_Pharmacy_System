package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.dao.util.DBUtil;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.BatchDAO;
import lk.ijse.royal_care_pharmacy.dto.BatchDTO;
import lk.ijse.royal_care_pharmacy.entity.Batch;
import lk.ijse.royal_care_pharmacy.entity.Customer;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BatchDAOImpl implements BatchDAO {
    private final Connection connection;

    public BatchDAOImpl(Connection connection) {
        this.connection=connection;
    }

    public static boolean addBatch(BatchDTO batch) throws SQLException, ClassNotFoundException {
        return Boolean.parseBoolean(null);
    }

    public static boolean updateBatch(BatchDTO batch) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Batch set BName=?,Description=?,UnitPrice=?,QTY=?,MdfExpDate=? where BatchId=?");
        preparedStatement.setObject(1,batch.getBName());
        preparedStatement.setObject(2,batch.getDescription());
        preparedStatement.setObject(3,batch.getUnitPrice());
        preparedStatement.setObject(4,batch.getQTY());
        preparedStatement.setObject(5,batch.getMdfExpDate());
        preparedStatement.setObject(6,batch.getBatchId());
        return preparedStatement.executeUpdate()>0;

    }

    public static boolean deleteBatch(String batchId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Batch where BatchId='"+batchId+"'")>0;
    }

    public static BatchDTO search(String batchId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Batch WHERE BatchId = ?";
        ResultSet result = CrudUtil.execute(sql, batchId);

        if (result.next()) {
            return new BatchDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5),
                    result.getString(6)
            );
        }
        return null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Batch save(lk.ijse.royal_care_pharmacy.entity.Batch batch) throws ViolationException, SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.Batch VALUES (?, ?, ?, ?, ?, ?)";
        boolean isAdded= DBUtil.executeUpdate(sql, batch.getBatchId(),batch.getBName(),batch.getDescription(), batch.getUnitPrice(),batch.getQTY(), batch.getMdfExpDate());
        return isAdded ? new Batch() : null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Batch update(lk.ijse.royal_care_pharmacy.entity.Batch entity) throws ViolationException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteByPk(String batchId) throws ViolationException, SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Batch where BatchId='"+batchId+"'")>0 ;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Batch> findAll() {
        return null;
    }

    @Override
    public Batch findByPk(String batchId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.Batch WHERE BatchId = ?";
        ResultSet result = CrudUtil.execute(sql, batchId);

        if (result.next()) {
            return new Batch(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5),
                    result.getString(6)
            );
        }
        return null;

    }

    @Override
    public boolean existByPk(String BatchId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM royal_care_pharmacy.batch WHERE BatchId=?";
        ResultSet resultSet = CrudUtil.execute(sql, BatchId);
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

}
