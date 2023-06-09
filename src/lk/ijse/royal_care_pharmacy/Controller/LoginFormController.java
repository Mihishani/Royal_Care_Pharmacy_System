package lk.ijse.royal_care_pharmacy.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LoginFormController {
    public TextField txtPassword;
    public TextField txtUserName;
    public Button btnLogin;
    public Button btnForgotPassword;
    public AnchorPane UserLoginFormContext;
    public AnchorPane UserLoginFormContext2;
    public Label lblTime;
    public Label lblDate;
    public Button btnBack;

    public void PasswordOnAction(ActionEvent actionEvent) {
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        System.out.println(txtUserName.getText());
        String tempUsername =txtUserName.getText();
        String tempPassword =txtPassword.getText();
        System.out.println(txtPassword.getText());
        if (tempUsername.equals("Mihishani") && tempPassword.equals("2001")) {
            Navigation.navigate(Routes.DASHBOARD, UserLoginFormContext);
            System.out.println(txtPassword.getText());
        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "Try again!").show();
        }
    }
    public void ForgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.NEWACCOUNTPAGE,UserLoginFormContext);
    }

    public void lblTimeOnAction(MouseEvent mouseEvent) {
    }

    public void lblDateOnAction(MouseEvent mouseEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.USERSPAGE,UserLoginFormContext);
    }
    public void initialize(){
        setDate();
        setTime();
    }
    public void setDate(){
        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO,e -> {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblDate.setText(LocalDateTime.now().format(formatter));
        }),new KeyFrame(javafx.util.Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    public void setTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
