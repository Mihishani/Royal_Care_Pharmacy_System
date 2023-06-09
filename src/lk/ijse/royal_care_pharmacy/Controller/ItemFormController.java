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
import lk.ijse.royal_care_pharmacy.dto.ItemDTO;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.ItemDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.ItemService;
import lk.ijse.royal_care_pharmacy.util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ItemFormController implements Initializable {
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
    public ItemService itemService;

    public void ItemNameOnAction(ActionEvent actionEvent) {
    }

    public void ItemIdOnAction(ActionEvent actionEvent) {
        String ItemId=txtItemId.getText();
        try {
            ItemDTO item= ItemDAOImpl.search(ItemId);
            if (item !=null){
                fillData(item);
            }
        }catch (SQLException|ClassNotFoundException exception){
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

    public void btnItemUpdateOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, ClassNotFoundException, SQLException {
        String ItemId=txtItemId.getText();
        String Item_Name=txtItemName.getText();
        double Price=Double.parseDouble(txtItemPrice.getText());
        String MadeOfDate=txtMAdeOfDay.getText();
        String ExpireOfDate=txtExpireDay.getText();
        int QTY=Integer.parseInt(txtQty.getText());
        String Description=txtDescription.getText();

        ItemDTO item=new ItemDTO(ItemId,Item_Name,Price,MadeOfDate,ExpireOfDate,QTY,Description);
        itemService.updateItem(item);
        //boolean isUpdate = ItemDAOImpl.updateItem(item);
        if (item!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Update Succesfully").show();
            loadItemDetail();
        }else{
            new Alert(Alert.AlertType.WARNING,"Item Update Failed").show();
            loadItemDetail();
        }
    }

    public void ItemDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ItemId=txtItemId.getText();
        String Item_Name=txtItemName.getText();
        double Price=Double.parseDouble(txtItemPrice.getText());
        String MadeOfDate=txtMAdeOfDay.getText();
        String ExpireOfDate=txtExpireDay.getText();
        int QTY=Integer.parseInt(txtQty.getText());
        String Description=txtDescription.getText();

        ItemDTO item=new ItemDTO(ItemId,Item_Name,Price,MadeOfDate,ExpireOfDate,QTY,Description);
        itemService.DeleteItem(item.getItemId());
        //boolean isDelete = ItemDAOImpl.deleteItem(item.getItemId());
        if (item!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Delete Succesfully").show();
            loadItemDetail();
        }else{
            new Alert(Alert.AlertType.WARNING,"Item Delete Failed").show();
            loadItemDetail();
        }
    }

    public void btnItemViewLoadOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadItemDetail();
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

    public void btnAddOnAction(ActionEvent actionEvent) throws CrudDAO.ViolationException, SQLException, ClassNotFoundException {
        String ItemId=txtItemId.getText();
        String Item_Name=txtItemName.getText();
        double Price=Double.parseDouble(txtItemPrice.getText());
        String MadeOfDate=txtMAdeOfDay.getText();
        String ExpireOfDate=txtExpireDay.getText();
        int QTY=Integer.parseInt(txtQty.getText());
        String Description=txtDescription.getText();

        ItemDTO item=new ItemDTO(ItemId,Item_Name,Price,MadeOfDate,ExpireOfDate,QTY,Description);
        ItemDTO itemDTO=itemService.saveItem(item);
        // boolean isAdded = ItemDAOImpl.addItem(item);
        if (itemDTO!=null){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Added Succesfully").show();
            loadItemDetail();
        }else{
            new Alert(Alert.AlertType.WARNING,"Item Added Failed").show();
        }
    }

    public void txtItemOnReleased(KeyEvent keyEvent) {
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
        Pattern idPattern = Pattern.compile("^(I00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern pricePatten = Pattern.compile("^[A-z0-9 ]{2,15}$");
        Pattern MDatePattern = Pattern.compile("^[A-z-0-9 ]{2,30}$" );
        Pattern EDatePattern = Pattern.compile( "^[A-z-0-9 ]{2,30}$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,10}$");
        Pattern descriptionPattern= Pattern.compile("^[A-z ]{3,15}$");



        if(!(idPattern.matcher(txtItemId.getText()).matches())){
            addError(txtItemId);
            return  txtItemId;
        }else{
            removeError(txtItemId);
            if((!namePatten.matcher( txtItemName.getText()).matches())){
                addError(txtItemName);
                return txtItemName;
            }else{
                removeError(txtItemName);
                if(!(pricePatten.matcher(txtItemPrice.getText()).matches())){
                    addError(txtItemPrice);
                    return txtItemPrice;
                }else {
                    removeError(txtItemPrice);
                    if(!(MDatePattern.matcher(txtMAdeOfDay.getText()).matches())){
                        addError(txtMAdeOfDay);
                        return txtMAdeOfDay;
                    }else{
                        removeError(txtMAdeOfDay);
                        if(!(EDatePattern.matcher(txtExpireDay.getText()).matches())){
                            addError(txtExpireDay);
                            return txtExpireDay;
                        }else{
                            removeError(txtExpireDay);
                            if(!(qtyPattern.matcher(txtQty.getText()).matches())){
                                addError(txtQty);
                                return txtQty;
                            }else{
                                removeError(txtQty);
                                if(!(descriptionPattern.matcher(txtDescription.getText()).matches())){
                                    addError(txtDescription);
                                    return  txtDescription;
                                }else{
                                    removeError(txtDescription);

                                }
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
            itemService= ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
}
