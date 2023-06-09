package lk.ijse.royal_care_pharmacy.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import java.io.IOException;

public class DashBoardController {
    public Button btnEmployee;
    public Button btnSalary;
    public Button btnAttendence;
    public Button btnItem;
    public Button btnOrder;
    public Button btnOrderDetail;
    public Button btnBactch;
    public Button btnSupplier;
    public Button btnPayment;
    public AnchorPane Pane1;
    public AnchorPane Pane2;
    public Button btnCustomer;
    public Button btnBack;


    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYEE,Pane2);
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SALARY,Pane2);
    }

    public void btnAttendenceOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ATTENDENCE,Pane2);
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM,Pane2);
    }

    public void BtnOrderOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ORDER,Pane2);
    }

    public void btnOrderDetailOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ORDERDETAIL,Pane2);
    }

    public void btnBatchOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.STOCK,Pane2);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,Pane2);
    }
    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PAYMENT,Pane2);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,Pane2);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.USERSPAGE,Pane1);
    }
}

