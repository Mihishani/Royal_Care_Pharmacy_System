package lk.ijse.royal_care_pharmacy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dto.SupplierDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.SupplierDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.SupplierService;
import lk.ijse.royal_care_pharmacy.service.custom.impl.SupplierServiceImpl;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SupplierFormController {
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public TextField txtSupplierAddress;
    public TextField txtsupplierEmailOnAction;
    public TextField txtSupplierTelNumber;
    public Button btnAddSupplier;
    public Button btnDeleteSupplier;
    public Button btnUpdateSupplier;
    public Button btnNext;
    public AnchorPane SupplierFormContext;
    public TableView tblViewSupplier;
    public Button btnViewSupplier;
    public TextField txtsupplierEmail;
    public TableColumn clmSupId;
    public TableColumn clmSupName;
    public TableColumn clmSupAddress;
    public TableColumn clmSupEmail;
    public TableColumn clmSupTelNum;
    public SupplierService supplierService;


    public void SupplierIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SupId = txtSupplierId.getText();
        SupplierDTO supplier = SupplierDAOImpl.search(SupId);
        if (supplier != null) {
            fillData(supplier);
        }
    }

    private void fillData(SupplierDTO supplier) {
        txtSupplierId.setText(supplier.getSupId());
        txtSupplierName.setText(supplier.getSName());
        txtSupplierAddress.setText(supplier.getAddress());
        txtsupplierEmail.setText(supplier.getEmail());
        txtSupplierTelNumber.setText(String.valueOf(supplier.getTelNumber()));
    }

    public void SupplierNameOnAction(ActionEvent actionEvent) {
    }

    public void SupplierAddressOnAction(ActionEvent actionEvent) {
    }

    public void SupplierEmailOnAction(ActionEvent actionEvent) {
    }

    public void SupplierTelNumberOnAction(ActionEvent actionEvent) {
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        String SupId = txtSupplierId.getText();
        String SName = txtSupplierName.getText();
        String Address = txtSupplierAddress.getText();
        String Email = txtsupplierEmail.getText();
        int TelNumber = Integer.parseInt(txtSupplierTelNumber.getText());

        SupplierDTO supplier = new SupplierDTO(SupId, SName, Address, Email, TelNumber);
        supplierService.saveSupplier(supplier);
        //boolean isAdded = SupplierServiceImpl.addSupplier(supplier);
        if (supplier!=null) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
        }
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SupId = txtSupplierId.getText();
        String SName = txtSupplierName.getText();
        String Address = txtSupplierAddress.getText();
        String Email = txtsupplierEmail.getText();
        int TelNumber = Integer.parseInt(txtSupplierTelNumber.getText());

        SupplierDTO supplier = new SupplierDTO(SupId, SName, Address, Email, TelNumber);
        supplierService.DeleteSupplier(supplier.getSupId());
        //boolean isDelete = SupplierServiceImpl.deleteSupplier(supplier.getSupId());
        if (supplier!=null) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted").show();
        }
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, ClassNotFoundException {
        String SupId = txtSupplierId.getText();
        String SName = txtSupplierName.getText();
        String Address = txtSupplierAddress.getText();
        String Email = txtsupplierEmail.getText();
        int TelNumber = Integer.parseInt(txtSupplierTelNumber.getText());

        SupplierDTO supplier = new SupplierDTO(SupId, SName, Address, Email, TelNumber);
        supplierService.updateSupplier(supplier);
        // boolean isUpdate = SupplierDAOImpl.updateSupplier(supplier);
        if (supplier!=null) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
        }
    }

    public void ViewSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        initialize();
    }

    private void initialize() throws SQLException, ClassNotFoundException {
        supplierService= ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIER);
        clmSupId.setCellValueFactory(new PropertyValueFactory("SupId"));
        clmSupName.setCellValueFactory(new PropertyValueFactory("SName"));
        clmSupAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        clmSupEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        clmSupTelNum.setCellValueFactory(new PropertyValueFactory("TelNumber"));
        try {
            loadSupplierDetail();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void loadSupplierDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM royal_care_pharmacy.supplier");
        ObservableList<SupplierDTO> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new SupplierDTO(
                            resultSet.getString("SupId"),
                            resultSet.getString("SName"),
                            resultSet.getString("Address"),
                            resultSet.getString("Email"),
                            resultSet.getInt("TelNumber")

                    )
            );

        }
        tblViewSupplier.setItems(observableList);
    }

    public void txtSupplierOnReleased(KeyEvent keyEvent) {
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
        Pattern idPattern = Pattern.compile("^(S00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9,!#$%&'*+/=?'{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-z0-9-]+)*$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,10}$");



        if(!(idPattern.matcher(txtSupplierId.getText()).matches())){
            addError(txtSupplierId);
            return txtSupplierId;
        }else{
            removeError(txtSupplierId);
            if((!namePatten.matcher( txtSupplierName.getText()).matches())){
                addError(txtSupplierName);
                return txtSupplierName;
            }else{
                removeError(txtSupplierName);
                if(!(addressPattern.matcher(txtSupplierAddress.getText()).matches())){
                    addError(txtSupplierAddress);
                    return txtSupplierAddress;
                }else {
                    removeError(txtSupplierAddress);
                    if(!(emailPattern.matcher(txtsupplierEmail.getText()).matches())){
                        addError(txtsupplierEmail);
                        return txtsupplierEmail;
                    }else{
                        removeError(txtsupplierEmail);
                        if(!(contactPattern.matcher(txtSupplierTelNumber.getText()).matches())){
                            addError(txtSupplierTelNumber);
                            return txtSupplierTelNumber;
                        }else{
                            removeError(txtSupplierTelNumber);


                        }
                    }
                }
            }
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: lightblue");btnAddSupplier.setDisable(false);
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red");
        btnAddSupplier.setDisable(true);
    }
}
