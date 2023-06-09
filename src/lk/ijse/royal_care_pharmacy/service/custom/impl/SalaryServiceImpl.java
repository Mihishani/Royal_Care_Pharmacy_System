package lk.ijse.royal_care_pharmacy.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dao.DaoFactory;
import lk.ijse.royal_care_pharmacy.dao.DaoTypes;
import lk.ijse.royal_care_pharmacy.dao.custom.SalaryDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.royal_care_pharmacy.service.custom.SalaryService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.SalaryDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalaryServiceImpl implements SalaryService {
    public TextField txtemployeeId;
    public TextField txtSalaryId;
    public TextField txtAmount;
    public Button btnAddSalary;
    public Button btnUpdateSalary;
    public Button btnDeleteSalary;
    public Button btnLoad;
    public TextField txtemployeeName;
    public TextField txtMonth;
    public TextField txtPaidOrNot;
    public TableView clmTableView;
    public TableColumn clmSalId;
    public TableColumn clmEmployeeId;
    public TableColumn clmEmployeeName;
    public TableColumn clmAmount;
    public TableColumn clmMonth;
    public TableColumn clmPaidOrNot;

    private final SalaryDAO salaryDAO;
    private final Convertor convertor;
    private final Connection connection;

    public SalaryServiceImpl()  {
        connection= DBConnection.getDbConnection().getConnection();
        salaryDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.SALARY);
        convertor=new Convertor();
    }

    public void btnLoadOnAction(ActionEvent actionEvent) {
        initialize();
    }

    private void initialize() {
        clmEmployeeId.setCellValueFactory(new PropertyValueFactory("EID"));
        clmEmployeeName.setCellValueFactory(new PropertyValueFactory("EName"));
        clmSalId.setCellValueFactory(new PropertyValueFactory("SalId"));
        clmAmount.setCellValueFactory(new PropertyValueFactory("Amount"));
        clmMonth.setCellValueFactory(new PropertyValueFactory("PMonth"));
        clmPaidOrNot.setCellValueFactory(new PropertyValueFactory("PaidOrNot"));
        try{
            loadSalaryDetail();
        }catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
    }

    private void loadSalaryDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM royal_care_pharmacy.Salary");
        ObservableList<SalaryDTO> observableList= FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(
                    new SalaryDTO(
                            resultSet.getString("EID"),
                            resultSet.getString("EName"),
                            resultSet.getString("SalId"),
                            resultSet.getDouble("Amount"),
                            resultSet.getString("PMonth"),
                            resultSet.getString("PaidOrNot")

                    )
            );

        }
        clmTableView.setItems(observableList);
    }

    public void deleteSalarayOnAction(ActionEvent actionEvent) {
        String EID=txtemployeeId.getText();
        String EName=txtemployeeName.getText();
        String SalId=txtSalaryId.getText();
        double Amount=Double.parseDouble(txtAmount.getText());
        String PMonth=txtMonth.getText();
        String PaidOrNot=txtPaidOrNot.getText();

        SalaryDTO salary=new SalaryDTO(EID,EName,SalId,Amount,PMonth,PaidOrNot);
        try {
            boolean isDelete = SalaryDAOImpl.deleteSalary(salary.getSalId());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Deleted Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Deleted Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnUpdateSalaryOnAction(ActionEvent actionEvent) {
        String EID=txtemployeeId.getText();
        String EName=txtemployeeName.getText();
        String SalId=txtSalaryId.getText();
        double Amount=Double.parseDouble(txtAmount.getText());
        String PMonth=txtMonth.getText();
        String PaidOrNot=txtPaidOrNot.getText();

        SalaryDTO salary=new SalaryDTO(EID,EName,SalId,Amount,PMonth,PaidOrNot);
        try {
            boolean isUpdate = SalaryDAOImpl.updateSalary(salary);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Update Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Update Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnAddSalaryOnAction(ActionEvent actionEvent) {
        String EID=txtemployeeId.getText();
        String EName=txtemployeeName.getText();
        String SalId=txtSalaryId.getText();
        double Amount=Double.parseDouble(txtAmount.getText());
        String PMonth=txtMonth.getText();
        String PaidOrNot=txtPaidOrNot.getText();

        SalaryDTO salary=new SalaryDTO(EID,EName,SalId,Amount,PMonth,PaidOrNot);
        try {
            boolean isAdded = SalaryDAOImpl.addSalary(salary);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Added Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Salary Added Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }

    }

    public void amountOnAction(ActionEvent actionEvent) {
    }

    public void salaryIdOnAction(ActionEvent actionEvent) {
        String SalId=txtSalaryId.getText();
        try {
            SalaryDTO salary=SalaryDAOImpl.search(SalId);
            if (salary !=null){
                fillData(salary);
            }
        }catch (SQLException|ClassNotFoundException exception){
            throw new RuntimeException(exception);
        }
    }

    private void fillData(SalaryDTO salary) {
        txtemployeeId.setText(salary.getEID());
        txtemployeeName.setText(salary.getEName());
        txtSalaryId.setText(salary.getSalId());
        txtAmount.setText(String.valueOf(salary.getAmount()));
        txtMonth.setText(salary.getPMonth());
        txtPaidOrNot.setText(salary.getPaidOrNot());
    }

    public void employeeIdOnAction(ActionEvent actionEvent) {
    }

    public void employeeNameOnAction(ActionEvent actionEvent) {
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.SalaryDTO saveSalary(lk.ijse.royal_care_pharmacy.dto.SalaryDTO salaryDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if (salaryDAO.existByPk(salaryDTO.getSalId()))throw new DuplicateException("Salary Already saved");
        salaryDAO.save(convertor.toSalary(salaryDTO));
        return salaryDTO;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.SalaryDTO updateSalary(lk.ijse.royal_care_pharmacy.dto.SalaryDTO salaryDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        if (!salaryDAO.existByPk(salaryDTO.getSalId()))throw new DuplicateException("Salary not found");
        salaryDAO.update(convertor.toSalary(salaryDTO));
        return salaryDTO;
    }

    @Override
    public void DeleteSalary(String SalId) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!salaryDAO.existByPk(SalId))throw new NotFoundException("Salary Not found");
        try{
            salaryDAO.deleteByPk(SalId);
        }catch (CrudDAO.ViolationException e){
            throw new InUseException("Salary already in..");
        }
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.SalaryDTO> findAllSalary() {
        return salaryDAO.findAll().stream().map(salary -> convertor.fromSalary(salary)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.SalaryDTO findSalaryBySalId(String SalId) throws SQLException, NotFoundException, ClassNotFoundException {
        //Optional<lk.ijse.royal_care_pharmacy.entity.Salary> optionalSalary=salaryDAO.findByPk(SalId);
        //if (!optionalSalary.isPresent())throw new NotFoundException("Salary not found");
        return convertor.fromSalary(salaryDAO.findByPk(SalId));
    }
}
