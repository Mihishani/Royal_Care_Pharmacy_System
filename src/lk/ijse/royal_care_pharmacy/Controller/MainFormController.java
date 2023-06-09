package lk.ijse.royal_care_pharmacy.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;

import javax.management.StringValueExp;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainFormController {
    public Button btnNext;
    public AnchorPane MainFormContext;
    public Button btnLogin;
    public Label lblDate;
    public Label lblTime;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.USERSPAGE,MainFormContext);
    }

    public void lblDateOnAction(MouseEvent mouseEvent) {
    lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void lblTimeOnAction(MouseEvent mouseEvent) {
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

    public void setTime(){
        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO,e ->{
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }),new KeyFrame(javafx.util.Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
