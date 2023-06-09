package lk.ijse.royal_care_pharmacy.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserFormController {
    public AnchorPane UserLoginFormContext2;
    public Label lblTime;
    public Label lblDate;
    public Button btnManager;
    public Button btnStockManager;
    public Button btnCashier;
    public AnchorPane userFormContext;

    public void lblTimeOnAction(MouseEvent mouseEvent) {
    }

    public void lblDateOnAction(MouseEvent mouseEvent) {
    }

    public void btnManagerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.LOGINPAGE,userFormContext);
    }

    public void btnStockManagerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.STOCKMANAGERLOGINPAGE,userFormContext);
    }

    public void btnCashierOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIERLOGINPAGE,userFormContext);
    }
    public void initialize(){
        setDate();
        setTime();
    }

    public void setDate(){
        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblDate.setText(LocalDateTime.now().format(formatter));
        }),new KeyFrame(javafx.util.Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setTime(){
        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO,e ->{
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }),new KeyFrame(javafx.util.Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
