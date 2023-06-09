package lk.ijse.royal_care_pharmacy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dto.CustomerDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.CustomerService;
import lk.ijse.royal_care_pharmacy.service.custom.impl.CustomerServiceImpl;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerFormController implements Initializable {
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
    public CustomerService customerService ;

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
      try {
          String CusId=txtCustId.getText();
          String CusName=txtCustName.getText();
          String CusAge=txtCustAge.getText();
          String CusAddress=txtCustAddress.getText();
          int CusTelNumber=Integer.parseInt(txtCustTelNum.getText());

          CustomerDTO customer=new CustomerDTO(CusId,CusName,CusAge,CusAddress,CusTelNumber);
          CustomerDTO customerDTO = customerService.saveCustomer(customer);
          //   boolean isAdded = CustomerDAOImpl.addCustomer(customer);
          if (customerDTO!=null){
              new Alert(Alert.AlertType.CONFIRMATION,"Customer Added Succesfully").showAndWait();
              loadCustomerDetail();
          }
      } catch (SQLException | CrudDAO.ViolationException | ClassNotFoundException|RuntimeException e) {
         new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
      }
        txtCustId.clear();txtCustName.clear();txtCustAge.clear();txtCustAddress.clear();txtCustTelNum.clear();

    }


    public void btnDeleteCustomerOnAction(ActionEvent actionEvent)  {
       try {
           String CusId=txtCustId.getText();
           String CusName=txtCustName.getText();
           String CusAge=txtCustAge.getText();
           String CusAddress=txtCustAddress.getText();
           int CusTelNumber=Integer.parseInt(txtCustTelNum.getText());

           CustomerDTO customer=new CustomerDTO(CusId,CusName,CusAge,CusAddress,CusTelNumber);

           //customerService.DeleteCustomer(customer.getCusId());
          // CustomerDTO customerDTO = customerService.DeleteCustomer(customer.getCusId());
           //boolean isDelete= CustomerDAOImpl.deleteCustomer(customer.getCusId());
           boolean c = customerService.DeleteCustomer(customer.getCusId());
           if (c!=true){
               new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted").show();
               loadCustomerDetail();
           }
       }catch (SQLException| ClassNotFoundException|RuntimeException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }
    }

    public void btnCustomerUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, CrudDAO.ViolationException {
        String CusId=txtCustId.getText();
        String CusName=txtCustName.getText();
        String CusAge=txtCustAge.getText();
        String CusAddress=txtCustAddress.getText();
        int CusTelNumber=Integer.parseInt(txtCustTelNum.getText());
        CustomerDTO customer=new CustomerDTO(CusId,CusName,CusAge,CusAddress,CusTelNumber);
        customerService.updateCustomer(customer);
       // boolean isUpdate= CustomerDAOImpl.updateCustomer(customer);
        if (customer!=null){
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

    public void btnLoadOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadCustomerDetail();
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
            Connection connection=DBConnection.getInstance().getConnection();
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




    public void txtCustomerIdOnKeyReleased(KeyEvent keyEvent) {
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
        Pattern idPattern = Pattern.compile("^(C00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern agePattern = Pattern.compile("^[0-9]{2,3}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,10}$");

        if(!(idPattern.matcher(txtCustId.getText()).matches())){
            addError(txtCustId);
            return txtCustId;
        }else{
            removeError(txtCustId);
            if((!namePatten.matcher(txtCustName.getText()).matches())){
                addError(txtCustName);
                return txtCustName;
            }else{
                removeError(txtCustName);
                if(!(agePattern.matcher(txtCustAge.getText()).matches())){
                    addError(txtCustAge);
                    return txtCustAge;
                }else {
                    removeError(txtCustAge);
                    if(!(addressPattern.matcher(txtCustAddress.getText()).matches())){
                        addError(txtCustAddress);
                        return txtCustAddress;
                    }else{
                        removeError(txtCustAddress);
                        if(!(contactPattern.matcher(txtCustTelNum.getText()).matches())){
                            addError(txtCustTelNum);
                            return txtCustTelNum;
                        }else{
                            removeError(txtCustTelNum);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: lightblue");
        btnAdd.setDisable(false);
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red");
        btnAdd.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            customerService= ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
}
