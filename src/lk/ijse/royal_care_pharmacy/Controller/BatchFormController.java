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
import lk.ijse.royal_care_pharmacy.dto.BatchDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.BatchDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.BatchService;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class BatchFormController implements Initializable {
    public TextField txtBatchId;
    public TextField BatchName;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TextField txtSupplierId;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnAdd;
    public Button btnNext;
    public AnchorPane BatchFormContext;
    public TableView tblBatchView;
    public Button btnLoad;
    public TextField txtCategorie;
    public TableColumn clmBatchId;
    public TableColumn clmBatchName;
    public TableColumn clmDescription;
    public TableColumn clmUnitPrice;
    public TableColumn clmQty;
    public TableColumn clmExpMdfDate;
    public TextField txtExpMdfDate;
    public BatchService batchService;

    public void BatchIdOnAction(ActionEvent actionEvent) {
        String BatchId=txtBatchId.getText();
        try {
            BatchDTO batch= BatchDAOImpl.search(BatchId);
            if (batch !=null){
                fillData(batch);
            }
        }catch (SQLException|ClassNotFoundException exception){
            throw new RuntimeException(exception);
        }
    }

    private void fillData(BatchDTO batch) {
        txtBatchId.setText(batch.getBatchId());
        BatchName.setText(batch.getBName());
        txtDescription.setText(batch.getDescription());
        txtUnitPrice.setText(String.valueOf(batch.getUnitPrice()));
        txtQty.setText(String.valueOf(batch.getQTY()));
        txtExpMdfDate.setText(batch.getMdfExpDate());
    }

    public void BatchNameOnAction(ActionEvent actionEvent) {
    }

    public void DescriptionOnAction(ActionEvent actionEvent) {
    }

    public void UnitPriceOnAction(ActionEvent actionEvent) {
    }

    public void QTYOnAction(ActionEvent actionEvent) {
    }

    public void SupplierIdOnAction(ActionEvent actionEvent) {
    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       try {
           String BatchId=txtBatchId.getText();
           String BName=BatchName.getText();
           String Description=txtDescription.getText();
           double UnitPrice=Double.parseDouble(txtUnitPrice.getText());
           int QTY=Integer.parseInt(txtQty.getText());
           String MdfExpDate=txtExpMdfDate.getText();

           BatchDTO batch=new BatchDTO(BatchId,BName,Description,UnitPrice,QTY,MdfExpDate);
           batchService.DeleteBatch(batch.getBatchId());
           //boolean isDelete = BatchDAOImpl.deleteBatch(batch.getBatchId());
           if (batch!=null){
               new Alert(Alert.AlertType.CONFIRMATION,"Item Delete Successfully").show();
               loadBatchDetail();
           }
       }catch (SQLException| ClassNotFoundException|RuntimeException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }
        txtBatchId.clear();BatchName.clear();txtDescription.clear();txtUnitPrice.clear();txtQty.clear();txtExpMdfDate.clear();
    }

    public void UpdateOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        String BatchId=txtBatchId.getText();
        String BName=BatchName.getText();
        String Description=txtDescription.getText();
        double UnitPrice=Double.parseDouble(txtUnitPrice.getText());
        int QTY=Integer.parseInt(txtQty.getText());
        String MdfExpDate=txtExpMdfDate.getText();

        BatchDTO batch=new BatchDTO(BatchId,BName,Description,UnitPrice,QTY,MdfExpDate);
        batchService.updateBatch(batch);
        //boolean isUpdate = BatchDAOImpl.updateBatch(batch);
        if (batch!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Update Successfully").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Failed").show();
        }
    }

    public void AddOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        try {
            String BatchId=txtBatchId.getText();
            String BName=BatchName.getText();
            String Description=txtDescription.getText();
            double UnitPrice=Double.parseDouble(txtUnitPrice.getText());
            int QTY=Integer.parseInt(txtQty.getText());
            String MdfExpDate=txtExpMdfDate.getText();

            BatchDTO batch=new BatchDTO(BatchId,BName,Description,UnitPrice,QTY,MdfExpDate);
            BatchDTO batchDTO=batchService.saveBatch(batch);
            // boolean isAdded = BatchDAOImpl.addBatch(batch);
            if (batchDTO!=null){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Added").show();
                loadBatchDetail();
            }
            }catch (SQLException| CrudDAO.ViolationException|ClassNotFoundException|RuntimeException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        txtBatchId.clear();BatchName.clear();txtDescription.clear();txtUnitPrice.clear();txtQty.clear();txtExpMdfDate.clear();
    }


    public void LoadOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
         loadBatchDetail();
    }
    private void loadBatchDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM royal_care_pharmacy.batch");
        ObservableList<BatchDTO> observableList= FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(
                    new BatchDTO(
                            resultSet.getString("BatchId"),
                            resultSet.getString("BName"),
                            resultSet.getString("Description"),
                            resultSet.getDouble("UnitPrice"),
                            resultSet.getInt("QTY"),
                            resultSet.getString("MdfExpDate")

                    )
            );

        }
        tblBatchView.setItems(observableList);

    }


    public void txtBatchIdOnRelease(KeyEvent keyEvent) {
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
        Pattern idPattern = Pattern.compile("^(B00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern descriptionPattern= Pattern.compile("^[A-z ]{3,15}$");
        Pattern pricePatten = Pattern.compile("^[A-z0-9 ]{2,15}$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,10}$");
        Pattern MEDatePattern = Pattern.compile("^[A-z-0-9 ]{2,40}$" );


        if(!(idPattern.matcher(txtBatchId.getText()).matches())){
            addError(txtBatchId);
            return  txtBatchId;
        }else{
            removeError(txtBatchId);
            if((!namePatten.matcher(BatchName.getText()).matches())){
                addError(BatchName);
                return BatchName;
            }else{
                removeError(BatchName);
                if(!(descriptionPattern.matcher(txtDescription.getText()).matches())){
                    addError(txtDescription);
                    return txtDescription;
                }else {
                    removeError(txtDescription);
                    if(!(pricePatten.matcher(txtUnitPrice.getText()).matches())){
                        addError(txtUnitPrice);
                        return txtUnitPrice;
                    }else{
                        removeError(txtUnitPrice);
                        if(!(qtyPattern.matcher(txtQty.getText()).matches())){
                            addError(txtQty);
                            return txtQty;
                        }else{
                            removeError(txtQty);
                            if(!(MEDatePattern.matcher(txtExpMdfDate.getText()).matches())){
                                addError(txtExpMdfDate);
                                return txtExpMdfDate;
                            }else{
                                removeError(txtExpMdfDate);

                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: lightblue");btnAdd.setDisable(false);
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red");
        btnAdd.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            batchService= ServiceFactory.getInstance().getService(ServiceTypes.STOCK);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clmBatchId.setCellValueFactory(new PropertyValueFactory("BatchId"));
        clmBatchName.setCellValueFactory(new PropertyValueFactory("BName"));
        clmDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory("UnitPrice"));
        clmQty.setCellValueFactory(new PropertyValueFactory("QTY"));
        clmExpMdfDate.setCellValueFactory(new PropertyValueFactory("MdfExpDate"));
        try{
            loadBatchDetail();
        }catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
    }
}
