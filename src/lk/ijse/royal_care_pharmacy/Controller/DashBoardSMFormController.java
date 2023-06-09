package lk.ijse.royal_care_pharmacy.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import java.io.IOException;

public class DashBoardSMFormController {
    public AnchorPane pane2;
    public AnchorPane pane1;
    public Button btnItem;
    public Button btnBatch;
    public Button btnSupplier;
    public AnchorPane pane3;
    public Button btnBack;

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM,pane3);
    }

    public void btnBatchOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.STOCK,pane3);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane3);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.USERSPAGE,pane2);
    }
}
