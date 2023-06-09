package lk.ijse.royal_care_pharmacy.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dao.DaoFactory;
import lk.ijse.royal_care_pharmacy.dao.DaoTypes;
import lk.ijse.royal_care_pharmacy.dao.custom.EmployeeDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.exception.ViolationException;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.service.custom.EmployeeService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.EmployeeDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
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

    private EmployeeDAO employeeDAO;
    private final Convertor convertor;
    private final Connection connection;

    public EmployeeServiceImpl() throws SQLException, ClassNotFoundException {
        connection= DBConnection.getInstance().getConnection();
        employeeDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.EMPLOYEE);
        convertor=new Convertor();
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
        String EId=txtEId.getText();
        String EName=txtEName.getText();
        String EAddress=txtEAddress.getText();
        String Email=txtEEmail.getText();
        int ETelNumber=Integer.parseInt(txtETelNum.getText());
        String EPassword=txtPassword.getText();
        String User_Name=txtUserName.getText();

        EmployeeDTO employee=new EmployeeDTO(EId,EName,EAddress,Email,ETelNumber,EPassword,User_Name);
        try {
            boolean isAdded = EmployeeDAOImpl.addEmployee(employee);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Added").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnEmployeeUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String EId=txtEId.getText();
        String EName=txtEName.getText();
        String EAddress=txtEAddress.getText();
        String Email=txtEEmail.getText();
        int ETelNumber=Integer.parseInt(txtETelNum.getText());
        String EPassword=txtPassword.getText();
        String User_Name=txtUserName.getText();

        EmployeeDTO employee=new EmployeeDTO(EId,EName,EAddress,Email,ETelNumber,EPassword,User_Name);
        boolean isAdded = EmployeeDAOImpl.updateEmployee(employee);
        if (isAdded){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated").show();
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
        boolean isAdded = EmployeeDAOImpl.DeleteEmployee(employee.getEId());
        if (isAdded){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted").show();
        }
    }

    public void btnNextOnAction(ActionEvent actionEvent) throws IOException {

    }


    public void btnLoadOnAction(ActionEvent actionEvent) {

        initialize();
    }

    private void initialize() {
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
            if (employee !=null){
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


    @Override
    public lk.ijse.royal_care_pharmacy.dto.EmployeeDTO saveEmployee(lk.ijse.royal_care_pharmacy.dto.EmployeeDTO employeeDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if (employeeDAO.findByPk(employeeDTO.getEId())!=null) throw new DuplicateException("Employee already saved");
        employeeDAO.save(convertor.toEmployee(employeeDTO));
        return employeeDTO;

    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.EmployeeDTO updateEmployee(lk.ijse.royal_care_pharmacy.dto.EmployeeDTO employeeDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        if (!employeeDAO.existByPk(employeeDTO.getEId())) throw new NotFoundException("Employee not found");
        employeeDAO.update(convertor.toEmployee(employeeDTO));
        return employeeDTO;
    }

    @Override
    public EmployeeDTO DeleteEmployee(String EID) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!employeeDAO.existByPk(EID)) throw new NotFoundException("Employee not found");

        try {
            employeeDAO.deleteByPk(EID);
        }catch (ViolationException | CrudDAO.ViolationException e){
            throw new InUseException("Employee already in..");
        }
        return null;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.EmployeeDTO> findAllEmployees() {
        return employeeDAO.findAll().stream().map(employee -> convertor.fromEmployee(employee)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.EmployeeDTO findEmployeeByCusId(String EID) throws SQLException, NotFoundException, ClassNotFoundException {
        //Employee> optionalEmployee = employeeDAO.findByPk(EID);
       // if (!optionalEmployee.isPresent()) throw new NotFoundException("Employee not found");

        return convertor.fromEmployee(employeeDAO.findByPk(EID));
    }
}
