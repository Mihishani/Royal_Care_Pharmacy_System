package lk.ijse.royal_care_pharmacy.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dao.DaoFactory;
import lk.ijse.royal_care_pharmacy.dao.DaoTypes;
import lk.ijse.royal_care_pharmacy.dao.custom.AttendenceDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.AttendenceDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.exception.ViolationException;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.royal_care_pharmacy.service.custom.AttendenceService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.AttendenceDTO;
import lk.ijse.royal_care_pharmacy.dto.EmployeeDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendenceServiceImpl implements AttendenceService {
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

    private AttendenceDAO attendenceDAO;
    private final Convertor convertor;
    private final Connection connection;

    public AttendenceServiceImpl(){
        connection= DBConnection.getDbConnection().getConnection();
        attendenceDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.ATTENDENCE);
        convertor=new Convertor();
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        loadEmployeeIds();
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<String> idList = AttendenceDAOImpl.loadEmpIds();

        for (String EID : idList) {
            observableList.add(EID);
        }
        cmdEmployeeId.setItems(observableList);

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

    public void btnAddOnAction(ActionEvent actionEvent) {

        AttendenceDTO attendence=new AttendenceDTO(txtEmployeeNIC.getText(),String.valueOf(cmdEmployeeId.getValue()),txtName.getText(),txtDate.getText(),txtDescription.getText());
        try {
            boolean isAdded = AttendenceDAOImpl.addAttendence(attendence);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION," Added Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        AttendenceDTO attendence=new AttendenceDTO(txtEmployeeNIC.getText(),String.valueOf(cmdEmployeeId.getValue()),txtName.getText(),txtDate.getText(),txtDescription.getText());
        try {
            boolean isUpdate = AttendenceDAOImpl.updateAttendence(attendence);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION," Update Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnDeleteOnaction(ActionEvent actionEvent) {
        AttendenceDTO attendence=new AttendenceDTO(txtEmployeeNIC.getText(),String.valueOf(cmdEmployeeId.getValue()),txtName.getText(),txtDate.getText(),txtDescription.getText());
        try {
            boolean isDelete = AttendenceDAOImpl.deleteAttendence(attendence.getNIC());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION," Delete Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }



    public void btnLoadAttendenceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadInitialize();
    }

    private void loadInitialize() {
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

    @Override
    public lk.ijse.royal_care_pharmacy.dto.AttendenceDTO saveAttendence(lk.ijse.royal_care_pharmacy.dto.AttendenceDTO attendenceDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if(attendenceDAO.existByPk(attendenceDTO.getNIC()))throw new DuplicateException("Attend already saved");
        attendenceDAO.save(convertor.toAttendence(attendenceDTO));
        return attendenceDTO;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.AttendenceDTO updateAttendence(lk.ijse.royal_care_pharmacy.dto.AttendenceDTO attendenceDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        if(!attendenceDAO.existByPk(attendenceDTO.getNIC()))throw new NotFoundException("Attend not found");
        attendenceDAO.update(convertor.toAttendence(attendenceDTO));
        return attendenceDTO;
    }

    @Override
    public void DeleteAttendence(String NIC) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!attendenceDAO.existByPk(NIC))throw new NotFoundException("Attend not found");
        try{
            attendenceDAO.deleteByPk(NIC);
        }catch (ViolationException | CrudDAO.ViolationException e) {
            throw new InUseException("Attend already in..");
        }
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.AttendenceDTO> findAllAttendence() {
       return attendenceDAO.findAll().stream().map(attendence -> convertor.fromAttendence(attendence)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.AttendenceDTO findAttendenceByNic(String NIC) throws NotFoundException, SQLException, ClassNotFoundException {
        //Optional<lk.ijse.royal_care_pharmacy.entity.Attendence>OptionalAttendence=attendenceDAO.findByPk(NIC);
        //if (!OptionalAttendence.isPresent())throw new NotFoundException("Attends not found");
        return convertor.fromAttendence(attendenceDAO.findByPk(NIC));
    }
}
