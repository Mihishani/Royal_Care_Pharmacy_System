package lk.ijse.royal_care_pharmacy.util;



import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {

            case MAINPAGE:
                window.setTitle("MAINPAGE");
                initUI("MainForm.fxml");
                break;

            case LOGINPAGE:
                window.setTitle("LOGINPAGE");
                initUI("LoginForm.fxml");
                break;

            case SALARY:
                window.setTitle("SALARY");
                initUI("SalaryForm.fxml");
                break;

            case ATTENDENCE:
                window.setTitle("ATTENDENCE");
                initUI("AttendenceForm.fxml");
                break;

            case DASHBOARD:
                window.setTitle("DASHBOARD");
                initUI("DashBoard.fxml");
                break;

            case NEWACCOUNTPAGE:
                window.setTitle("NEWACCOUNTPAGE");
                initUI("CreateLoginAccount.fxml");
                break;


            case EMPLOYEE:
                window.setTitle("EMPLOYEE");
                initUI("EmployeeForm.fxml");
                break;

            case ITEM:
                window.setTitle("ITEM");
                initUI("ItemForm.fxml");
                break;

            case ORDER:
                window.setTitle("ORDER");
                initUI("OrderForm.fxml");
                break;

            case ORDERDETAIL:
                window.setTitle("ORDERDETAIL");
                initUI("OrderDetailForm.fxml");
                break;

            case STOCK:
                window.setTitle("STOCK");
                initUI("BatchForm.fxml");
                break;

            case PAYMENT:
                window.setTitle("PAYMENT");
                initUI("PaymentForm.fxml");
                break;

            case SUPPLIER:
                window.setTitle("SUPPLIER");
                initUI("SupplierForm.fxml");
                break;

            case CUSTOMER:
                window.setTitle("CUSTOMER");
                initUI("CustomerForm.fxml");
                break;

            case USERSPAGE:
                window.setTitle("USERSPAGE");
                initUI("UserForm.fxml");
                break;

            case CASHIERLOGINPAGE:
                window.setTitle("CASHIERLOGINPAGE");
                initUI("CashierLoginForm.fxml");
                break;

            case STOCKMANAGERLOGINPAGE:
                window.setTitle("STOCKMANAGERLOGINPAGE");
                initUI("StockManagerLoginForm.fxml");
                break;

            case DASHBOARDCASHIER:
                window.setTitle("DASHBOARDCASHIER");
                initUI("DashBoardCashier.fxml");
                break;

            case DASHBOARDSTM:
                window.setTitle("DASHBOARDSTM");
                initUI("DashBoardSMForm.fxml");
                break;

            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/royal_care_pharmacy/view/" + location)));
    }
}
