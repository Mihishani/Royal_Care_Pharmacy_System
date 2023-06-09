package lk.ijse.royal_care_pharmacy.service.custom;

import lk.ijse.royal_care_pharmacy.dto.OrderDTO;
import lk.ijse.royal_care_pharmacy.dto.OrderDetailDTO;
import lk.ijse.royal_care_pharmacy.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderService extends SuperService {
    boolean getCusOrder(ArrayList<OrderDetailDTO>detailDTOS, OrderDTO orderDTO)throws SQLException;
}
