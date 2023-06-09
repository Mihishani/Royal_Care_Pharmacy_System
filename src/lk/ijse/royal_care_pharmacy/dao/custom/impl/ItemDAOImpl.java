package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.dao.util.DBUtil;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.ItemDAO;
import lk.ijse.royal_care_pharmacy.dto.ItemDTO;
import lk.ijse.royal_care_pharmacy.entity.Item;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    private final Connection connection;

    public ItemDAOImpl(Connection connection) {
        this.connection=connection;
    }

    public static boolean addItem(ItemDTO item) throws SQLException, ClassNotFoundException {
       return Boolean.parseBoolean(null);
    }

    public static boolean updateItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Item set Item_Name=?,Price=?,MadeOfDate=?,ExpireOfDate=?,QTY=?,Description=? where ItemId=?");
        preparedStatement.setObject(1,item.getItem_Name());
        preparedStatement.setObject(2,item.getPrice());
        preparedStatement.setObject(3,item.getMadeOfDate());
        preparedStatement.setObject(4,item.getExpireOfDate());
        preparedStatement.setObject(5,item.getQTY());
        preparedStatement.setObject(6,item.getDescription());
        preparedStatement.setObject(7,item.getItemId());
        return preparedStatement.executeUpdate()>0;
    }

    public static boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Item where ItemId='"+itemId+"'")>0;
    }

    public static ItemDTO search(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.item WHERE ItemId = ?";
        ResultSet result = CrudUtil.execute(sql, itemId);

        if (result.next()) {
            return new ItemDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getString(4),
                    result.getString(5),
                    result.getInt(6),
                    result.getString(7)

            );
        }
        return null;
    }


    @Override
    public lk.ijse.royal_care_pharmacy.entity.Item save(lk.ijse.royal_care_pharmacy.entity.Item item) throws ViolationException, SQLException, ClassNotFoundException {
        String sql = "INSERT INTO royal_care_pharmacy.item VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean isAdd= DBUtil.executeUpdate(sql,item.getItemId(),item.getItem_Name(),item.getPrice(),item.getMadeOfDate(),item.getExpireOfDate(),item.getQTY(),item.getDescription());
        return isAdd ? new Item() : null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Item update(lk.ijse.royal_care_pharmacy.entity.Item item) throws ViolationException, ClassNotFoundException, SQLException {
        return CrudUtil.execute("UPDATE royal_care_pharmacy.Item set Item_Name=?,Price=?,MadeOfDate=?,ExpireOfDate=?,QTY=?,Description=? where ItemId=?",item.getItem_Name(),item.getPrice(),item.getMadeOfDate(),item.getExpireOfDate(),item.getQTY(),item.getDescription(),item.getItemId());
    }

    @Override
    public boolean deleteByPk(String itemId) throws ViolationException, SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Item where ItemId='"+itemId+"'")>0;

    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Item> findAll() {
        return null;
    }

    @Override
    public Item findByPk(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM royal_care_pharmacy.item WHERE ItemId = ?";
        ResultSet result = CrudUtil.execute(sql, itemId);

        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getString(4),
                    result.getString(5),
                    result.getInt(6),
                    result.getString(7)

            );
        }
        return null;
    }

    @Override
    public boolean existByPk(String itemId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM royal_care_pharmacy.item WHERE ItemId=?";
        ResultSet resultSet = CrudUtil.execute(sql, itemId);
        if (resultSet.next()) {
            return true;
        }
        return false;

    }

    /*@Override
    public boolean existByPk(String pk) {
        return false;
    }*/


}
