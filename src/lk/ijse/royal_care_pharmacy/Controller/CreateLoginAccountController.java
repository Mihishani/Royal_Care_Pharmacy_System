package lk.ijse.royal_care_pharmacy.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import java.io.IOException;
import java.util.Objects;

public class CreateLoginAccountController {
    public TextField txtUserName;
    public TextField txtPAssword;
    public TextField txtConfirmPassword;
    public RadioButton rdBtnShowPassword;
    public Button BtnNext;
    public AnchorPane CreateLoginAccountContext;
    public Button btnSave;

    public void UserNameOnAction(ActionEvent actionEvent) {
    }

    public void PasswordOnAction(ActionEvent actionEvent) {
    }

    public void confirmPasswordOnAction(ActionEvent actionEvent) {
    }

    public void ShowPasswordOnAction(ActionEvent actionEvent) {
    }

    public void NextOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGINPAGE,CreateLoginAccountContext);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
