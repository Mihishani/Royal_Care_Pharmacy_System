package lk.ijse.royal_care_pharmacy.service.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.OrderDAOImpl;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dto.OrderDTO;
import lk.ijse.royal_care_pharmacy.dto.OrderDetailDTO;
import lk.ijse.royal_care_pharmacy.service.custom.OrderService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean getCusOrder(ArrayList<OrderDetailDTO> detailDTOS, OrderDTO orderDTO) throws SQLException {
        Connection connection=null;

        try{
            connection=  DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved= OrderDAOImpl.saveOrder(orderDTO);
            if(isOrderSaved){
                boolean isDetailsSaved=OrderDAOImpl.saveOrderDetails(detailDTOS);
                if(isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"ERROR").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}
