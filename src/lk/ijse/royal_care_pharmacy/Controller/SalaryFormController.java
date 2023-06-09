package lk.ijse.royal_care_pharmacy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.SalaryDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.SalaryDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.SalaryService;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryFormController {
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
    public SalaryService salaryService;

    public void btnLoadOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        initialize();
    }

    private void initialize() throws SQLException, ClassNotFoundException {
       salaryService= ServiceFactory.getInstance().getService(ServiceTypes.SALARY);
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

    public void deleteSalarayOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String EID=txtemployeeId.getText();
        String EName=txtemployeeName.getText();
        String SalId=txtSalaryId.getText();
        double Amount=Double.parseDouble(txtAmount.getText());
        String PMonth=txtMonth.getText();
        String PaidOrNot=txtPaidOrNot.getText();

        SalaryDTO salary=new SalaryDTO(EID,EName,SalId,Amount,PMonth,PaidOrNot);
        salaryService.DeleteSalary(salary.getSalId());
        //boolean isDelete = SalaryDAOImpl.deleteSalary(salary.getSalId());
        if (salary!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Salary Deleted Succesfully").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Salary Deleted Failed").show();
        }
    }

    public void btnUpdateSalaryOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        String EID=txtemployeeId.getText();
        String EName=txtemployeeName.getText();
        String SalId=txtSalaryId.getText();
        double Amount=Double.parseDouble(txtAmount.getText());
        String PMonth=txtMonth.getText();
        String PaidOrNot=txtPaidOrNot.getText();

        SalaryDTO salary=new SalaryDTO(EID,EName,SalId,Amount,PMonth,PaidOrNot);
        salaryService.updateSalary(salary);
        //boolean isUpdate = SalaryDAOImpl.updateSalary(salary);
        if (salary!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Salary Update Succesfully").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Salary Update Failed").show();
        }
    }

    public void btnAddSalaryOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        String EID=txtemployeeId.getText();
        String EName=txtemployeeName.getText();
        String SalId=txtSalaryId.getText();
        double Amount=Double.parseDouble(txtAmount.getText());
        String PMonth=txtMonth.getText();
        String PaidOrNot=txtPaidOrNot.getText();

        SalaryDTO salary=new SalaryDTO(EID,EName,SalId,Amount,PMonth,PaidOrNot);
        salaryService.saveSalary(salary);
        //boolean isAdded = SalaryDAOImpl.addSalary(salary);
        if (salary!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Salary Added Succesfully").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Salary Added Failed").show();
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
}
