package lk.ijse.royal_care_pharmacy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.EmployeeDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.EmployeeService;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeFormController implements Initializable {
    public ImageView AchPaEmployeeForm;
    public TextField txtEId;
    public TextField txtEName;
    public TextField txtEAddress;
    public TextField txtEEmail;
    public TextField txtETelNum;
    public TextField txtUserName;
    public TextField txtPassword;
    public Button btnAddEmployee;
    public Button btnUpdateEmployee;
    public Button btnEmployeeDelete;
    public Button btnNext;
    public AnchorPane EmployeeContext;
    public TableView tblViewTable;
    public Button btnLoad;
    public TableColumn clmEid;
    public TableColumn clmEname;
    public TableColumn clmEAddress;
    public TableColumn clmEEmail;
    public TableColumn clmETelNum;
    public TableColumn clmUserName;
    public TableColumn clmEPassword;
    public EmployeeService employeeService;

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, CrudDAO.ViolationException {
        String EId=txtEId.getText();
        String EName=txtEName.getText();
        String EAddress=txtEAddress.getText();
        String Email=txtEEmail.getText();
        int ETelNumber=Integer.parseInt(txtETelNum.getText());
        String EPassword=txtPassword.getText();
        String User_Name=txtUserName.getText();

        EmployeeDTO employee=new EmployeeDTO(EId,EName,EAddress,Email,ETelNumber,EPassword,User_Name);
       EmployeeDTO employeeDTO= employeeService.saveEmployee(employee);
         //boolean isAdded = EmployeeDAOImpl.addEmployee(employee);
        if (employeeDTO!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Added").show();
            loadEmployeeDetail();
        }
    }

    public void btnEmployeeUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, CrudDAO.ViolationException {
        String EId=txtEId.getText();
        String EName=txtEName.getText();
        String EAddress=txtEAddress.getText();
        String Email=txtEEmail.getText();
        int ETelNumber=Integer.parseInt(txtETelNum.getText());
        String EPassword=txtPassword.getText();
        String User_Name=txtUserName.getText();

        EmployeeDTO employee=new EmployeeDTO(EId,EName,EAddress,Email,ETelNumber,EPassword,User_Name);
        EmployeeDTO employeeDTO=employeeService.updateEmployee(employee);
       // boolean isAdded = EmployeeDAOImpl.updateEmployee(employee);
        if (employeeDTO!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated").show();
            loadEmployeeDetail();
        }
    }

    public void btnDeleteEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String EId=txtEId.getText();
        String EName=txtEName.getText();
        String EAddress=txtEAddress.getText();
        String Email=txtEEmail.getText();
        int ETelNumber=Integer.parseInt(txtETelNum.getText());
        String EPassword=txtPassword.getText();
        String User_Name=txtUserName.getText();

        EmployeeDTO employee=new EmployeeDTO(EId,EName,EAddress,Email,ETelNumber,EPassword,User_Name);
        employeeService.DeleteEmployee(employee.getEId());
        //boolean isAdded = EmployeeDAOImpl.DeleteEmployee(employee.getEId());
        if (employee!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted").show();
            loadEmployeeDetail();
        }
    }

    public void btnNextOnAction(ActionEvent actionEvent) throws IOException {

    }


    public void btnLoadOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        loadEmployeeDetail();
    }



    private void loadEmployeeDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM royal_care_pharmacy.employee");
        ObservableList<EmployeeDTO> observableList= FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(
                    new EmployeeDTO(
                            resultSet.getString("EId"),
                            resultSet.getString("EName"),
                            resultSet.getString("EAddress"),
                            resultSet.getString("Email"),
                            resultSet.getInt("ETelNumber"),
                            resultSet.getString("EPassword"),
                            resultSet.getString("User_Name")

                    )
            );

        }
        tblViewTable.setItems(observableList);
    }


    public void txtEidOnAction(ActionEvent actionEvent) {
        String EId=txtEId.getText();
        try {
            EmployeeDTO employee=EmployeeDAOImpl.search(EId);
            if (employee!=null){
                fillData(employee);
            }
        }catch (SQLException|ClassNotFoundException exception){
            throw new RuntimeException(exception);
        }
    }

    private void fillData(EmployeeDTO employee) {
        txtEId.setText(employee.getEId());
        txtEName.setText(employee.getEName());
        txtEAddress.setText(employee.getEAddress());
        txtEEmail.setText(employee.getEmail());
        txtETelNum.setText(String.valueOf(employee.getETelNumber()));
        txtPassword.setText(employee.getEPassword());
        txtUserName.setText(employee.getUser_Name());
    }

    public void txtEmployeeIdOnReleased(KeyEvent keyEvent) {
        validate();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validate();

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Valid");
            }
        }

    }




    private Object validate(){
        Pattern idPattern = Pattern.compile("^(E00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9,!#$%&'*+/=?'{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-z0-9-]+)*$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,10}$");
        Pattern passwordPattern= Pattern.compile("^[A-z-0-9 ]{1,10}$");
        Pattern userNamePattern= Pattern.compile("^[A-z-0-9 ]{1,15}$");


        if(!(idPattern.matcher(txtEId.getText()).matches())){
            addError(txtEId);
            return txtEId;
        }else{
            removeError(txtEId);
            if((!namePatten.matcher(txtEName.getText()).matches())){
                addError(txtEName);
                return txtEName;
            }else{
                removeError(txtEName);
                if(!(addressPattern.matcher(txtEAddress.getText()).matches())){
                    addError(txtEAddress);
                    return txtEAddress;
                }else {
                    removeError(txtEAddress);
                    if(!(emailPattern.matcher(txtEEmail.getText()).matches())){
                        addError(txtEEmail);
                        return txtEEmail;
                    }else{
                        removeError(txtEEmail);
                        if(!(contactPattern.matcher(txtETelNum.getText()).matches())){
                            addError(txtETelNum);
                            return txtETelNum;
                        }else{
                            removeError(txtETelNum);
                            if(!(passwordPattern.matcher(txtPassword.getText()).matches())){
                                addError(txtPassword);
                                return txtPassword;
                            }else {
                                removeError(txtPassword);
                                if(!(userNamePattern.matcher(txtUserName.getText()).matches())){
                                    addError(txtUserName);
                                    return txtUserName;
                                }else {
                                    removeError(txtUserName);
                                }
                            }

                        }
                    }
                }
            }
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: lightblue");btnAddEmployee.setDisable(false);
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red");
         btnAddEmployee.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            employeeService= ServiceFactory.getInstance().getService(ServiceTypes.EMPLOYEE);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clmEid.setCellValueFactory(new PropertyValueFactory("EId"));
        clmEname.setCellValueFactory(new PropertyValueFactory("EName"));
        clmEAddress.setCellValueFactory(new PropertyValueFactory("EAddress"));
        clmEEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        clmETelNum.setCellValueFactory(new PropertyValueFactory("ETelNumber"));
        clmEPassword.setCellValueFactory(new PropertyValueFactory("EPassword"));
        clmUserName.setCellValueFactory(new PropertyValueFactory("User_Name"));

        try{
            loadEmployeeDetail();
        }catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
    }
}
