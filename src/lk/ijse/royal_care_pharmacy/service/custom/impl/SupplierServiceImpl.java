package lk.ijse.royal_care_pharmacy.service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dao.CrudDAO;
import lk.ijse.royal_care_pharmacy.dao.DaoFactory;
import lk.ijse.royal_care_pharmacy.dao.DaoTypes;
import lk.ijse.royal_care_pharmacy.dao.custom.SupplierDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.exception.ViolationException;
import lk.ijse.royal_care_pharmacy.service.custom.SupplierService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.SupplierDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {
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

    private final SupplierDAO supplierDAO;
    private final Convertor convertor;
    private final Connection connection;

    public SupplierServiceImpl() {
        connection= DBConnection.getDbConnection().getConnection();
        supplierDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.SUPPLIER);
        convertor=new Convertor();
    }

    public static boolean addSupplier(SupplierDTO supplier) {

        return false;
    }

    public static boolean deleteSupplier(String supId) {
        return false;
    }

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

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        String SupId = txtSupplierId.getText();
        String SName = txtSupplierName.getText();
        String Address = txtSupplierAddress.getText();
        String Email = txtsupplierEmail.getText();
        int TelNumber = Integer.parseInt(txtSupplierTelNumber.getText());

        SupplierDTO supplier = new SupplierDTO(SupId, SName, Address, Email, TelNumber);
        try {
            boolean isAdded = SupplierDAOImpl.addSupplier(supplier);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        String SupId = txtSupplierId.getText();
        String SName = txtSupplierName.getText();
        String Address = txtSupplierAddress.getText();
        String Email = txtsupplierEmail.getText();
        int TelNumber = Integer.parseInt(txtSupplierTelNumber.getText());

        SupplierDTO supplier = new SupplierDTO(SupId, SName, Address, Email, TelNumber);
        try {
            boolean isDelete = SupplierDAOImpl.deleteSupplier(supplier.getSupId());
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        String SupId = txtSupplierId.getText();
        String SName = txtSupplierName.getText();
        String Address = txtSupplierAddress.getText();
        String Email = txtsupplierEmail.getText();
        int TelNumber = Integer.parseInt(txtSupplierTelNumber.getText());

        SupplierDTO supplier = new SupplierDTO(SupId, SName, Address, Email, TelNumber);
        try {
            boolean isUpdate = SupplierDAOImpl.updateSupplier(supplier);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void ViewSupplierOnAction(ActionEvent actionEvent) {
        initialize();
    }

    private void initialize() {
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


    @Override
    public lk.ijse.royal_care_pharmacy.dto.SupplierDTO saveSupplier(lk.ijse.royal_care_pharmacy.dto.SupplierDTO supplierDTO) throws DuplicateException, CrudDAO.ViolationException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if (supplierDAO.existByPk(supplierDTO.getSupId()))throw new DuplicateException("Supplier Already saved");
        supplierDAO.save(convertor.toSupplier(supplierDTO));
        return supplierDTO;
    }

    @Override
    public SupplierDTO updateSupplier(SupplierDTO supplierDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException {
        return null;
    }
/*
    @Override
    public static lk.ijse.royal_care_pharmacy.dto.SupplierDTO updateSupplier(lk.ijse.royal_care_pharmacy.dto.SupplierDTO supplierDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException {
        if (!supplierDAO.existByPk(supplierDTO.getSupId()))throw new DuplicateException("Supplier not found");
        supplierDAO.update(convertor.toSupplier(supplierDTO));
       // return supplierDTO;
    }*/

    @Override
    public void DeleteSupplier(String SupId) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!supplierDAO.existByPk(SupId))throw new NotFoundException("Supplier Not found");
        try{
            supplierDAO.deleteByPk(SupId);
        }catch (ViolationException | CrudDAO.ViolationException e){
            throw new InUseException("Supplier already in..");
        }
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.SupplierDTO> findAllSupplier() {
        return supplierDAO.findAll().stream().map(supplier -> convertor.fromSupplier(supplier)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.SupplierDTO findSupplierBySupId(String SupId) throws SQLException, NotFoundException, ClassNotFoundException {
       // Optional<lk.ijse.royal_care_pharmacy.entity.Supplier> optionalSupplier=supplierDAO.findByPk(SupId);
        //if (!optionalSupplier.isPresent())throw new NotFoundException("Salary not found");
        return convertor.fromSupplier(supplierDAO.findByPk(SupId));
    }
}
