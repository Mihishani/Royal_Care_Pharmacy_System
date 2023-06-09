package lk.ijse.royal_care_pharmacy.dao.custom.impl;

import lk.ijse.royal_care_pharmacy.dao.custom.OrderDAO;
import lk.ijse.royal_care_pharmacy.dto.OrderDTO;
import lk.ijse.royal_care_pharmacy.dto.OrderDetailDTO;
import lk.ijse.royal_care_pharmacy.entity.Order;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private  Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection=connection;
    }

    public OrderDAOImpl() {

    }

    public static ArrayList<String> loadCusIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT CusId FROM royal_care_pharmacy.customer";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static ArrayList<String> loadCIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ItemId FROM royal_care_pharmacy.item";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static boolean saveOrder(OrderDTO order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO royal_care_pharmacy.orders VALUES (?,?,?)",
                 order.getOID(),order.getCusId(),order.getODate());
    }

    public static boolean saveOrderDetails(ArrayList<OrderDetailDTO> details) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO od:details
        ) {
            boolean isDetailSaved=CrudUtil.execute("INSERT INTO royal_care_pharmacy.order_detail VALUES (?,?,?,?)",
                    od.getOID(),od.getItemId(),od.getPrice(),od.getQty());
            if(isDetailSaved){
                if (!updateQty(od.getItemId(),od.getQty())){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String itemId, String qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE royal_care_pharmacy.item SET QTY=QTY-? WHERE ItemId=?",qty,itemId);
    }

    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {
        String OID = getOrderId();
        if (OID != null) {
            String[] split = OID.split("D0");
            int OrderId = Integer.parseInt(split[1]);
            OrderId += 1;
            return "D0" + OrderId;
        }
        return "D01";
    }

    private static String getOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT OID FROM royal_care_pharmacy.orders ORDER BY OID DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }


    @Override
    public lk.ijse.royal_care_pharmacy.entity.Order save(lk.ijse.royal_care_pharmacy.entity.Order entity) throws ViolationException {
        return null;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.entity.Order update(lk.ijse.royal_care_pharmacy.entity.Order entity) throws ViolationException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteByPk(String pk) throws ViolationException {

        return false;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.entity.Order> findAll() {
        return null;
    }

    @Override
    public Order findByPk(String pk) {
        return null;
    }

    /*@Override
    public boolean existByPk(String pk) {
        return false;
    }*/


}
