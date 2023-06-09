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
import lk.ijse.royal_care_pharmacy.dao.custom.ItemDAO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.ItemDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.exception.ViolationException;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.service.custom.ItemService;
import lk.ijse.royal_care_pharmacy.service.exception.DuplicateException;
import lk.ijse.royal_care_pharmacy.service.exception.InUseException;
import lk.ijse.royal_care_pharmacy.service.exception.NotFoundException;
import lk.ijse.royal_care_pharmacy.service.util.Convertor;
import lk.ijse.royal_care_pharmacy.dto.ItemDTO;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {
    public TextField ItemName;
    public TextField txtItemId;
    public TextField txtItemPrice;
    public TextField txtQty;
    public Button btnOnAction;
    public Button btnItemUpdate;
    public Button btnItemDelete;
    public Button btnNext;
    public TextField txtSupplierId;
    public TextField txtBAtchId;
    public AnchorPane ItemFormContext;
    public TableView tblItemView;
    public Button btnItemViewLoad;
    public TextField txtMAdeOfDay;
    public TextField txtExpireDay;
    public ComboBox cmdBatchId;
    public TableColumn clmItemId;
    public TableColumn clmItemName;
    public TableColumn clmItemPrice;
    public TableColumn clmItemMFD;
    public TableColumn clmEPD;
    public TableColumn clmQTY;
    public TableColumn clmCategorie;
    public ComboBox cmdCategorie;
    public TextField txtDescription;
    public TableColumn clmDescription;
    public TextField txtItemName;
    public Button btnAdd;

    private final ItemDAO itemDAO;
    private final Convertor convertor;
    private final Connection connection;

    public ItemServiceImpl() {
        connection= DBConnection.getDbConnection().getConnection();
        itemDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.ITEM);
        convertor=new Convertor();
    }

    public void ItemNameOnAction(ActionEvent actionEvent) {
    }

    public void ItemIdOnAction(ActionEvent actionEvent) {
        String ItemId=txtItemId.getText();
        try {
            ItemDTO item= ItemDAOImpl.search(ItemId);
            if (item !=null){
                fillData(item);
            }
        }catch (SQLException |ClassNotFoundException exception){
            throw new RuntimeException(exception);
        }
    }

    private void fillData(ItemDTO item) {
        txtItemId.setText(item.getItemId());
        txtItemName.setText(item.getItem_Name());
        txtItemPrice.setText(String.valueOf(item.getPrice()));
        txtMAdeOfDay.setText(item.getMadeOfDate());
        txtExpireDay.setText(item.getExpireOfDate());
        txtQty.setText(String.valueOf(item.getQTY()));
        txtDescription.setText(item.getDescription());
    }

    public void ItemPriceOnAction(ActionEvent actionEvent) {
    }

    public void QtyOnAction(ActionEvent actionEvent) {
    }

    public void btnItemUpdateOnAction(ActionEvent actionEvent) {
        String ItemId=txtItemId.getText();
        String Item_Name=txtItemName.getText();
        double Price=Double.parseDouble(txtItemPrice.getText());
        String MadeOfDate=txtMAdeOfDay.getText();
        String ExpireOfDate=txtExpireDay.getText();
        int QTY=Integer.parseInt(txtQty.getText());
        String Description=txtDescription.getText();

        ItemDTO item=new ItemDTO(ItemId,Item_Name,Price,MadeOfDate,ExpireOfDate,QTY,Description);
        try {
            boolean isUpdate = ItemDAOImpl.updateItem(item);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Update Succesfully").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Item Update Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void ItemDeleteOnAction(ActionEvent actionEvent) {
        String ItemId=txtItemId.getText();
        String Item_Name=txtItemName.getText();
        double Price=Double.parseDouble(txtItemPrice.getText());
        String MadeOfDate=txtMAdeOfDay.getText();
        String ExpireOfDate=txtExpireDay.getText();
        int QTY=Integer.parseInt(txtQty.getText());
        String Description=txtDescription.getText();

        ItemDTO item=new ItemDTO(ItemId,Item_Name,Price,MadeOfDate,ExpireOfDate,QTY,Description);
        try {
            boolean isDelete = ItemDAOImpl.deleteItem(item.getItemId());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Delete Succesfully").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Item Delete Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public void btnItemViewLoadOnAction(ActionEvent actionEvent) {
        initialize();
    }

    private void initialize() {
        clmItemId.setCellValueFactory(new PropertyValueFactory("ItemId"));
        clmItemName.setCellValueFactory(new PropertyValueFactory("Item_Name"));
        clmItemPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        clmItemMFD.setCellValueFactory(new PropertyValueFactory("MadeOfDate"));
        clmEPD.setCellValueFactory(new PropertyValueFactory("ExpireOfDate"));
        clmQTY.setCellValueFactory(new PropertyValueFactory("QTY"));
        clmDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        try{
            loadItemDetail();
        }catch (ClassNotFoundException | SQLException exception){
            exception.printStackTrace();
        }
    }

    private void loadItemDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM royal_care_pharmacy.item");
        ObservableList<ItemDTO> observableList= FXCollections.observableArrayList();
        while (resultSet.next()){
            observableList.add(
                    new ItemDTO(
                            resultSet.getString("ItemId"),
                            resultSet.getString("Item_Name"),
                            resultSet.getDouble("Price"),
                            resultSet.getString("MadeOfDate"),
                            resultSet.getString("ExpireOfDate"),
                            resultSet.getInt("QTY"),
                            resultSet.getString("Description")

                    )
            );

        }
        tblItemView.setItems(observableList);
    }

    public void txtMadeOfDayOnAction(ActionEvent actionEvent) {
    }

    public void txtExpireDayOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String ItemId=txtItemId.getText();
        String Item_Name=txtItemName.getText();
        double Price=Double.parseDouble(txtItemPrice.getText());
        String MadeOfDate=txtMAdeOfDay.getText();
        String ExpireOfDate=txtExpireDay.getText();
        int QTY=Integer.parseInt(txtQty.getText());
        String Description=txtDescription.getText();

        ItemDTO item=new ItemDTO(ItemId,Item_Name,Price,MadeOfDate,ExpireOfDate,QTY,Description);
        try {
            boolean isAdded = ItemDAOImpl.addItem(item);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Added Succesfully").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Item Added Failed").show();
            }
        }catch (ClassNotFoundException | SQLException exception){
            throw new RuntimeException(exception);
        }
    }


    @Override
    public lk.ijse.royal_care_pharmacy.dto.ItemDTO saveItem(lk.ijse.royal_care_pharmacy.dto.ItemDTO itemDTO) throws DuplicateException, CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        if (itemDAO.existByPk(itemDTO.getItemId())) throw new DuplicateException("Item already saved");
        itemDAO.save(convertor.toItem(itemDTO));
        return itemDTO;
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.ItemDTO updateItem(lk.ijse.royal_care_pharmacy.dto.ItemDTO itemDTO) throws NotFoundException, CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        if (!itemDAO.existByPk(itemDTO.getItemId())) throw new NotFoundException("Item not found");
        itemDAO.update(convertor.toItem(itemDTO));
        return itemDTO;
    }

    @Override
    public void DeleteItem(String ItemId) throws NotFoundException, SQLException, ClassNotFoundException {
        if (!itemDAO.existByPk(ItemId)) throw new NotFoundException("Item not found");

        try {
            itemDAO.deleteByPk(ItemId);
        }catch (ViolationException | CrudDAO.ViolationException e){
            throw new InUseException("Item already in used");
        }
    }

    @Override
    public List<lk.ijse.royal_care_pharmacy.dto.ItemDTO> findAllItem() {
        return itemDAO.findAll().stream().map(item -> convertor.fromItem(item)).collect(Collectors.toList());
    }

    @Override
    public lk.ijse.royal_care_pharmacy.dto.ItemDTO findItemByItemId(String ItemId) throws SQLException, NotFoundException, ClassNotFoundException {
        //Optional<lk.ijse.royal_care_pharmacy.entity.Item> optionalItem = itemDAO.findByPk(ItemId);
        //if (!optionalItem.isPresent()) throw new NotFoundException("Item not found");
        return convertor.fromItem(itemDAO.findByPk(ItemId));
    }
}
