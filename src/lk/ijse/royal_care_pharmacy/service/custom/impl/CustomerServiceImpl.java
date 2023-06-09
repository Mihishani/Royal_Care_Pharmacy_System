package lk.ijse.royal_care_pharmacy.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dao.DaoFactory;
import lk.ijse.royal_care_pharmacy.dao.DaoTypes;
import lk.ijse.royal_care_pharmacy.dao.custom.CustomerDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.exception.ViolationException;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.service.custom.CustomerService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.CustomerDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    public AnchorPane CustomerPane;
    public TextField txtCustId;
    public TextField txtCustName;
    public TextField txtCustAge;
    public TextField txtCustAddress;
    public TextField txtCustTelNum;
    public TableView tblViewTable;
    public TableColumn clmCustId;
    public TableColumn clmCustName;
    public TableColumn clmCustAge;
    public TableColumn clmCustAddress;
    public TableColumn clmCustTelNum;
    public Button btnPrint;
    public Button btnAdd;

    private CustomerDAO customerDAO;
    private  Convertor convertor;
    private  Connection connection;

    public CustomerServiceImpl() {
        try {
            connection=DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.CUSTOMER);
        convertor=new Convertor();
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String CusId=txtCustId.getText();
        String CusName=txtCustName.getText();
        String CusAge=txtCustAge.getText();
        String CusAddress=txtCustAddress.getText();
        int CusTelNumber=Integer.parseInt(txtCustTelNum.getText());

        CustomerDTO customer=new CustomerDTO(CusId,CusName,CusAge,CusAddress,CusTelNumber);
        try {
            boolean isAdded = CustomerDAOImpl.addCustomer(customer);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added Succesfully").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
        txtCustId.clear();txtCustName.clear();txtCustAge.clear();txtCustAddress.clear();txtCustTelNum.clear();

    }


    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        String CusId=txtCustId.getText();
        String CusName=txtCustName.getText();
        String CusAge=txtCustAge.getText();
        String CusAddress=txtCustAddress.getText();
        int CusTelNumber=Integer.parseInt(txtCustTelNum.getText());

        CustomerDTO customer=new CustomerDTO(CusId,CusName,CusAge,CusAddress,CusTelNumber);


        try {
            boolean isDelete= CustomerDAOImpl.deleteCustomer(customer.getCusId());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnCustomerUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String CusId=txtCustId.getText();
        String CusName=txtCustName.getText();
        String CusAge=txtCustAge.getText();
        String CusAddress=txtCustAddress.getText();
        int CusTelNumber=Integer.parseInt(txtCustTelNum.getText());

        CustomerDTO customer=new CustomerDTO(CusId,CusName,CusAge,CusAddress,CusTelNumber);
        boolean isUpdate= CustomerDAOImpl.updateCustomer(customer);
        if (isUpdate){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated").show();
        }
    }

    public void txtCustIdOnAction(ActionEvent actionEvent) {
        String id=txtCustId.getText();
        try {
            CustomerDTO customer=CustomerDAOImpl.search(id);
            if (customer !=null){
                fillData(customer);
            }
        }catch (SQLException|ClassNotFoundException exception){
            throw new RuntimeException(exception);
        }
    }

    private void fillData(CustomerDTO customer) {
        txtCustId.setText(customer.getCusId());
        txtCustName.setText(customer.getCusName());
        txtCustAge.setText(customer.getCusAge());
        txtCustAddress.setText(customer.getCusAddress());
        txtCustTelNum.setText(String.valueOf(customer.getCusTelNumber()));
    }

    public void btnLoadOnAction(ActionEvent actionEvent) {

        initialize();
    }

    private void initialize() {
        clmCustId.setCellValueFactory(new PropertyValueFactory("CusId"));
        clmCustName.setCellValueFactory(new PropertyValueFactory("CusName"));
        clmCustAge.setCellValueFactory(new PropertyValueFactory("CusAge"));
        clmCustAddress.setCellValueFactory(new PropertyValueFactory("CusAddress"));
        clmCustTelNum.setCellValueFactory(new PropertyValueFactory("CusTelNumber"));
        try{
            loadCustomerDetail();
        }catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
    }

    private void loadCustomerDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM royal_care_pharmacy.customer");
        ObservableList<CustomerDTO> observableList= FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(
                    new CustomerDTO(
                            resultSet.getString("CusId"),
                            resultSet.getString("CusName"),
                            resultSet.getString("CusAge"),
                            resultSet.getString("CusAddress"),
                            resultSet.getInt("CusTelNumber")

                    )
            );

        }
        tblViewTable.setItems(observableList);
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        try{
            JasperDesign load= JRXmlLoader.load(this.getClass().getResourceAsStream("/lk/ijse/royal_care_pharmacy/util/report/CustomerReport.jrxml"));
            Connection connection= DBConnection.getInstance().getConnection();
            JasperReport compileReport= JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint= JasperFillManager.fillReport(compileReport,null,connection);
            JasperViewer.viewReport(jasperPrint,false);


        }catch (JRException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public lk.ijse.royal_care_pharmacy.dto.CustomerDTO saveCustomer(lk.ijse.royal_care_pharmacy.dto.CustomerDTO customerDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if (customerDAO.findByPk(customerDTO.getCusId())!=null) throw new DuplicateException("Customer already saved");
        customerDAO.save(convertor.toCustomer(customerDTO));
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        if (!customerDAO.existByPk(customerDTO.getCusId())) throw new NotFoundException("Customer not found");
        customerDAO.update(convertor.toCustomer(customerDTO));
        return customerDTO;
    }

    @Override
    public boolean DeleteCustomer(String CusId) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!customerDAO.existByPk(CusId)) throw new NotFoundException("Customer not found");
        try {
            customerDAO.deleteByPk(CusId);
        }catch (ViolationException | CrudDAO.ViolationException e){
            throw new InUseException("Customer already in..");
        }
        return  false;
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.CustomerDTO> findAllCustomer() {
         return customerDAO.findAll().stream().map(customer -> convertor.fromCustomer(customer)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.CustomerDTO findCustomerByCusId(String CusId) throws SQLException, NotFoundException, ClassNotFoundException {
        //Optional<lk.ijse.royal_care_pharmacy.entity.Customer> optionalCustomer = customerDAO.findByPk(CusId);
        //if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");

        return convertor.fromCustomer(customerDAO.findByPk(CusId));
    }
}
