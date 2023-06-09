package lk.ijse.royal_care_pharmacy.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import java.io.IOException;

public class DashBoardCashierController {
    public AnchorPane pane2;
    public AnchorPane pane1;
    public Button btnItem;
    public Button btnCustomer;
    public Button btnOrder;
    public Button btnPayment;
    public AnchorPane pane3;
    public Button btnBack;
    public AnchorPane Pane;

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM,pane3);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane3);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ORDER,pane3);
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PAYMENT,pane3);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.USERSPAGE,Pane);
    }
}
