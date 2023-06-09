package lk.ijse.royal_care_pharmacy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.AttendenceDTO;
import lk.ijse.royal_care_pharmacy.dto.EmployeeDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.AttendenceDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.AttendenceService;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendenceFormController {
   // public TextField txtEmployeeId;
    public TextField txtEmployeeNIC;
    public TextField txtDate;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView tblAttendence;
    public TextField txtMarkAttend;
    public Button btnLoadAttendenceView;
    public TableColumn clmENic;
    public TableColumn clmDate;
    public TextField txtEmployeeNIC1;
    public TextField txtEmployeeName;
    public TableView tblAttendenceView;
    public TableColumn clmEId;
    public TableColumn clmEName;
    public ComboBox clmEmployeeId;
    public ComboBox cmdEmployeeId;
    public TextField txtDescription;
    public TextField txtName;
    public TableColumn clmDescription;
    //public AttendenceService attendenceService;

    public void initialize(){
        loadEmployeeIds();
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = AttendenceDAOImpl.loadEmpIds();

            for (String EID : idList) {
                observableList.add(EID);
            }
            cmdEmployeeId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void cmdEIDOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String EID = String.valueOf(cmdEmployeeId.getValue());
        // txtName.setText(AttendenceModel.getAttendence(String.valueOf(cmdEmployeeId.getValue())).getEName());
        try {
            AttendenceDTO attendence =AttendenceDAOImpl.search(EID);
            EmployeeDTO employee= EmployeeDAOImpl.search(EID);
            fillData(employee);
            fillData(attendence);
            txtName.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillData(EmployeeDTO employee) {

        txtName.setText(employee.getEName());
    }
    private void fillData(AttendenceDTO attendence) {
        txtEmployeeNIC.setText(attendence.getNIC());
        txtName.setText(attendence.getEName());
        txtDate.setText(attendence.getADate());
        txtDescription.setText(attendence.getDescription());
    }

    private void fillAttendenceDate(AttendenceDTO attendence) {
        txtEmployeeNIC.setText(attendence.getNIC());
        //txtEmployeeId.setText(attendence.getEID());
        txtName.setText(attendence.getEName());
        txtDate.setText(attendence.getADate());
        txtDescription.setText(attendence.getDescription());
    }

    public void dateOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        AttendenceDTO attendence=new AttendenceDTO(txtEmployeeNIC.getText(),String.valueOf(cmdEmployeeId.getValue()),txtName.getText(),txtDate.getText(),txtDescription.getText());
       // attendenceService.saveAttendence(attendence);
         boolean isAdded = AttendenceDAOImpl.addAttendence(attendence);
        if (isAdded){
            new Alert(Alert.AlertType.CONFIRMATION," Added Succesfully").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        AttendenceDTO attendence=new AttendenceDTO(txtEmployeeNIC.getText(),String.valueOf(cmdEmployeeId.getValue()),txtName.getText(),txtDate.getText(),txtDescription.getText());
        //attendenceService.updateAttendence(attendence);
        boolean isUpdate = AttendenceDAOImpl.updateAttendence(attendence);
        if (isUpdate){
            new Alert(Alert.AlertType.CONFIRMATION," Update Succesfully").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
        }
    }

    public void btnDeleteOnaction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        AttendenceDTO attendence=new AttendenceDTO(txtEmployeeNIC.getText(),String.valueOf(cmdEmployeeId.getValue()),txtName.getText(),txtDate.getText(),txtDescription.getText());
       // attendenceService.DeleteAttendence(attendence.getNIC());
         boolean isDelete = AttendenceDAOImpl.deleteAttendence(attendence.getNIC());
        if (isDelete){
            new Alert(Alert.AlertType.CONFIRMATION," Delete Succesfully").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
        }
    }



    public void btnLoadAttendenceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        loadInitialize();
    }

    private void loadInitialize() throws SQLException, ClassNotFoundException {
        //attendenceService= ServiceFactory.getInstance().getService(ServiceTypes.ATTENDENCE);
        clmENic.setCellValueFactory(new PropertyValueFactory("NIC"));
        clmEId.setCellValueFactory(new PropertyValueFactory("EID"));
        clmEName.setCellValueFactory(new PropertyValueFactory("EName"));
        clmDate.setCellValueFactory(new PropertyValueFactory("ADate"));
        clmDescription.setCellValueFactory(new PropertyValueFactory("Description"));

        try{
            loadAttendenceDetail();
        }catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
    }

    private void loadAttendenceDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM royal_care_pharmacy.Attendence");
        ObservableList<AttendenceDTO> observableList= FXCollections.observableArrayList();

        while (resultSet.next()){
            observableList.add(
                    new AttendenceDTO(
                            resultSet.getString("NIC"),
                            resultSet.getString("EID"),
                            resultSet.getString("EName"),
                            resultSet.getString("ADate"),
                            resultSet.getString("Description")

                    )
            );

        }
        System.out.println(observableList.get(0));
        tblAttendenceView.setItems(observableList);
    }

    public void employeeNameOnAction(ActionEvent actionEvent) {
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
    }


    public void employeeNicOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if(keyEvent.getCode() == KeyCode.ENTER){
            String nic = txtEmployeeNIC.getText();
            AttendenceDTO search = AttendenceDAOImpl.search(nic);
            fillAttendenceDate(search);
        }
    }
}
