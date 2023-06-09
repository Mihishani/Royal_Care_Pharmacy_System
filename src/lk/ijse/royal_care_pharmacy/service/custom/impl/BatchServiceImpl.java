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
import lk.ijse.royal_care_pharmacy.dao.custom.BatchDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.BatchDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.exception.ViolationException;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.service.custom.BatchService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.BatchDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BatchServiceImpl implements BatchService {
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

    private final BatchDAO batchDAO;
    private final Convertor convertor;
    private final Connection connection;

    public BatchServiceImpl() {
        connection= DBConnection.getDbConnection().getConnection();
        batchDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.STOCK);
        convertor=new Convertor();
    }

    public void BatchIdOnAction(ActionEvent actionEvent) {
        String BatchId=txtBatchId.getText();
        try {
            BatchDTO batch= BatchDAOImpl.search(BatchId);
            if (batch !=null){
                fillData(batch);
            }
        }catch (SQLException |ClassNotFoundException exception){
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

    public void DeleteOnAction(ActionEvent actionEvent) {
        String BatchId=txtBatchId.getText();
        String BName=BatchName.getText();
        String Description=txtDescription.getText();
        double UnitPrice=Double.parseDouble(txtUnitPrice.getText());
        int QTY=Integer.parseInt(txtQty.getText());
        String MdfExpDate=txtExpMdfDate.getText();

        BatchDTO batch=new BatchDTO(BatchId,BName,Description,UnitPrice,QTY,MdfExpDate);
        try {
            boolean isDelete = BatchDAOImpl.deleteBatch(batch.getBatchId());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Delete Successfully").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        String BatchId=txtBatchId.getText();
        String BName=BatchName.getText();
        String Description=txtDescription.getText();
        double UnitPrice=Double.parseDouble(txtUnitPrice.getText());
        int QTY=Integer.parseInt(txtQty.getText());
        String MdfExpDate=txtExpMdfDate.getText();

        BatchDTO batch=new BatchDTO(BatchId,BName,Description,UnitPrice,QTY,MdfExpDate);
        try {
            boolean isUpdate = BatchDAOImpl.updateBatch(batch);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Update Successfully").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void AddOnAction(ActionEvent actionEvent) {
        String BatchId=txtBatchId.getText();
        String BName=BatchName.getText();
        String Description=txtDescription.getText();
        double UnitPrice=Double.parseDouble(txtUnitPrice.getText());
        int QTY=Integer.parseInt(txtQty.getText());
        String MdfExpDate=txtExpMdfDate.getText();

        BatchDTO batch=new BatchDTO(BatchId,BName,Description,UnitPrice,QTY,MdfExpDate);
        try {
            boolean isAdded = BatchDAOImpl.addBatch(batch);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Added").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }


    public void LoadOnAction(ActionEvent actionEvent) {
        initialize();
    }

    private void initialize() {
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


    @Override
    public lk.ijse.royal_care_pharmacy.dto.BatchDTO saveBatch(lk.ijse.royal_care_pharmacy.dto.BatchDTO batchDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if (batchDAO.existByPk(batchDTO.getBatchId()))throw new DuplicateException("Batch Already saved");
        batchDAO.save(convertor.toBatch(batchDTO));
        return batchDTO;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.BatchDTO updateBatch(lk.ijse.royal_care_pharmacy.dto.BatchDTO batchDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException {
         if (!batchDAO.existByPk(batchDTO.getBatchId()))throw new NotFoundException("Batch not found");
         batchDAO.update(convertor.toBatch(batchDTO));
         return batchDTO;
    }

    @Override
    public void DeleteBatch(String BatchId) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!batchDAO.existByPk(BatchId))throw new NotFoundException("Batch Not found");
        try{
            batchDAO.deleteByPk(BatchId);
        }catch (ViolationException | CrudDAO.ViolationException e){
            throw new InUseException("Batch already in..");
        }
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.BatchDTO> findAllBatch() {
        return batchDAO.findAll().stream().map(batch -> convertor.fromBatch(batch)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.BatchDTO findBatchByBatchId(String BatchId) throws SQLException, NotFoundException, ClassNotFoundException {
        //Optional<lk.ijse.royal_care_pharmacy.entity.Batch>optionalBatch=batchDAO.findByPk(BatchId);
        //if (!optionalBatch.isPresent())throw new NotFoundException("Book not found");
        return convertor.fromBatch(batchDAO.findByPk(BatchId));
    }
}
