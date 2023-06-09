package lk.ijse.royal_care_pharmacy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.royal_care_pharmacy.db.DBConnection;
import lk.ijse.royal_care_pharmacy.dto.*;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.ItemDAOImpl;
import lk.ijse.royal_care_pharmacy.dao.custom.impl.OrderDAOImpl;
import lk.ijse.royal_care_pharmacy.service.ServiceFactory;
import lk.ijse.royal_care_pharmacy.service.ServiceTypes;
import lk.ijse.royal_care_pharmacy.service.custom.CustomerService;
import lk.ijse.royal_care_pharmacy.service.custom.ItemService;
import lk.ijse.royal_care_pharmacy.service.custom.OrderDetailService;
import lk.ijse.royal_care_pharmacy.service.custom.OrderService;
import lk.ijse.royal_care_pharmacy.util.Navigation;
import lk.ijse.royal_care_pharmacy.util.Routes;
import lk.ijse.royal_care_pharmacy.view.tm.PlaceOrderTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public ComboBox cmdOrderId;
    public ComboBox cmdItemId;
    public TextField txtDate;
    public TextField txtDescription;
    public TextField txtPrice;
    public TableView tblOrderView;
    public TextField txtQty;
    public Button btnAdd;
    public Button btnRemove;
    public Button btnPlacerOrder;
    public TextField txtTotal;
    public TextField txtOrderId;
    public TextField txtItemId;
    public TextField txtCustId;
    public TextField txtCustNAme;
    public Button btnNew;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTotal;
    public TableColumn clmOrderId;
    public TableColumn clmCustomerId;
    public TableColumn clmItemId;
    public TableColumn clmItemName;
    public TableColumn clmCusId;
    public TableColumn clmDescription;
    public TableColumn clmQTy;
    public TableColumn clmPrice;
    public TextField txtItemName;
    public ComboBox cmdCustomerId;
    public TextField txtQty1;
    public TableColumn clmQty;
    public TableColumn clmAction;
    public TextField txtQtyOnHand;
    public TableColumn clmTotal;
    public TextField txtCustPayment;
    public Label lblBalance;
    public Button btnPrint;
    public AnchorPane pane;
    public OrderService orderService;
    public OrderDetailService orderDetailService;
    public CustomerService customerService;
    public ItemService itemService;
    private ObservableList<PlaceOrderTm> obList= FXCollections.observableArrayList();



    public void DescriptionOnAction(ActionEvent actionEvent) {
    }

    public void txtPriceOnAction(ActionEvent actionEvent) {
    }

    public void OrderViewOnAction(SortEvent<TableView> tableViewSortEvent) {
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {

        btnAddOnAction(actionEvent);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String ItemId = String.valueOf(cmdItemId.getValue());
        String ItemName=txtItemName.getText();
        String Description = txtDescription.getText();
        //int QtyOnHand= Integer.parseInt(txtQtyOnHand.getText());
        int QTY=Integer.parseInt(txtQty.getText());
        double Price = Double.parseDouble(txtPrice.getText());
        double total = Price * QTY;
        Button btnDelete = new Button("Delete");


        txtQty.setText("");

        if (!obList.isEmpty()) {
            L1:
            /* check same item has been in table. If so, update that row instead of adding new row to the table */
            for (int i = 0; i < tblOrderView.getItems().size(); i++) {
                if (clmItemId.getCellData(i).equals(ItemId)) {
                    QTY += (int) clmQty.getCellData(i);
                    total = Price * QTY;

                    obList.get(i).setQTY(QTY);
                    obList.get(i).setTotal(total);
                    tblOrderView.refresh();
                    return;
                }
            }
        }

        /* set delete button to some action before it put on obList */
        btnDelete.setOnAction((e) -> {
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                PlaceOrderTm tm = (PlaceOrderTm) tblOrderView.getSelectionModel().getSelectedItem();
                /*
                netTot = Double.parseDouble(txtNetTot.getText());
                netTot = netTot - tm.getTotalPrice();
                */

                tblOrderView.getItems().removeAll(tblOrderView.getSelectionModel().getSelectedItem());
            }
        });
        obList.add(new PlaceOrderTm(ItemId,ItemName,Description,QTY,Price, total,btnDelete));
        tblOrderView.setItems(obList);
        calculateTotal();
       // qtyChange();

    }

    private int calculateTotal() {
        int total=0;
        for (PlaceOrderTm tm: obList){
            total+=tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
        return (int) total ;
    }

    private void  setCellValueFactory() throws SQLException, ClassNotFoundException {

        clmItemId.setCellValueFactory(new PropertyValueFactory("ItemId"));
        clmItemName.setCellValueFactory(new PropertyValueFactory("ItemName"));
        clmDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        clmQty.setCellValueFactory(new PropertyValueFactory("QTY"));
        clmPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        clmTotal.setCellValueFactory(new PropertyValueFactory("total"));
        clmAction.setCellValueFactory(new PropertyValueFactory("btnDelete"));
    }
    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }
    public void transaction () throws SQLException {
        OrderDTO order=new OrderDTO(
                //lblOrderId.getText(),
                //"D004",
                lblOrderId.getText(),
                (String.valueOf(cmdCustomerId.getValue())),
                String.valueOf(lblDate.getText())

        );

        ArrayList<OrderDetailDTO> details=new ArrayList<>();
        for (PlaceOrderTm tm:obList
        ) {
            details.add(new OrderDetailDTO(
                    // lblOrderId.getText(),
                    /*"D003",
                    tm.getItemId(),
                    String.valueOf(tm.getPrice()),
                    String.valueOf(tm.getQTY())*/
                    //"D004",
                    lblOrderId.getText(),
                    tm.getItemId(),
                    String.valueOf(tm.getPrice()),
                    String.valueOf(tm.getQTY())

            ));
            boolean isAdded=orderService.getCusOrder(details,order);

        }

    }
    public void plaaceOrderOnAction(ActionEvent actionEvent) throws SQLException {
        /*OrderDTO order=new OrderDTO(
                 //lblOrderId.getText(),
                //"D004",
                lblOrderId.getText(),
                (String.valueOf(cmdCustomerId.getValue())),
                String.valueOf(lblDate.getText())

        );

        ArrayList<OrderDetailDTO> details=new ArrayList<>();
        for (PlaceOrderTm tm:obList
        ) {
            details.add(new OrderDetailDTO(
                   // lblOrderId.getText(),
                    /*"D003",
                    tm.getItemId(),
                    String.valueOf(tm.getPrice()),
                    String.valueOf(tm.getQTY())
                    //"D004",
                    lblOrderId.getText(),
                    tm.getItemId(),
                    String.valueOf(tm.getPrice()),
                    String.valueOf(tm.getQTY())

            ));
        }

        Connection connection=null;

        try{
            connection=  DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved=new OrderDAOImpl().saveOrder(order);
            if(isOrderSaved){
                boolean isDetailsSaved=new OrderDAOImpl().saveOrderDetails(details);
                if(isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"ERROR").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }*/
        transaction();
        qtyChange();
        paymentBalance();
    }

    private void paymentBalance() {
        int CustPay=Integer.valueOf(txtCustPayment.getText());
        int Balance=CustPay-calculateTotal();
        lblBalance.setText(String.valueOf(Balance));
    }

    public void custNameOnAction(ActionEvent actionEvent) {
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }

    public void lblOrderIdOnAction(MouseEvent mouseEvent) {
    }

    public void lblDateOnAction(MouseEvent mouseEvent) {
    }

    public void lblTotalOnAction(MouseEvent mouseEvent) {
    }

    public void cmdItemOnAction(ActionEvent actionEvent) {
        String ItemId = String.valueOf(cmdItemId.getValue());
        try {
           //ItemDTO item = ItemDAOImpl.search(ItemId);
            //Employee employee= EmployeeModel.search(EID);
             ItemDTO itemDTO=itemService.findItemByItemId(ItemId);
            fillDataItem(itemDTO);
            //fillData(attendence);
            txtQtyOnHand.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataItem(ItemDTO item) {
        txtItemName.setText(item.getItem_Name());
        txtDescription.setText(item.getDescription());
        txtQtyOnHand.setText(String.valueOf(item.getQTY()));
        txtPrice.setText(String.valueOf(item.getPrice()));
    }

    public void cmdOnAction(ActionEvent actionEvent) {
    }

    private void qtyChange(){
        int value =Integer.parseInt(txtQtyOnHand.getText());

        if (!txtQty.getText().equals("") && (value > 0)){
            int q = Integer.parseInt(txtQty.getText());
            int q2 = Integer.parseInt(txtQtyOnHand.getText());
            int result = q2-q;

            if (result <= 0){
                new Alert(Alert.AlertType.WARNING, "Out of stock");
            }else {
                txtQtyOnHand.setText(String.valueOf(result));
            }
        }
    }

   /* public void initialize() throws SQLException, ClassNotFoundException {
        loadCustomerIds();
        loadItemIds();
        setCellValueFactory();
        loadOrderDate();
        loadNextOrderId();
    }*/
    private void loadNextOrderId() {
        try {
            String OID = OrderDAOImpl.generateNextOrderId();
            lblOrderId.setText(OID);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadItemIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = OrderDAOImpl.loadCIds();

            for (String ItemId : idList) {
                observableList.add(ItemId);
            }
            cmdItemId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = OrderDAOImpl.loadCusIds();

            for (String CusId : idList) {
                observableList.add(CusId);
            }
            cmdCustomerId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmdCustOnAction(ActionEvent actionEvent) {
        String CusId = String.valueOf(cmdCustomerId.getValue());
        try {
            //CustomerDTO customer = CustomerDAOImpl.search(CusId);
            CustomerDTO customerDTO=customerService.findCustomerByCusId(CusId);
            fillData(customerDTO);

            txtCustNAme.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(CustomerDTO customer) {
        txtCustNAme.setText(customer.getCusName());

    }

    public void txtQtyHandOnAction(ActionEvent actionEvent) {
    }

    public void txtCustPaymentOnAction(ActionEvent actionEvent) {
    }

    public void lblBalanceOnAction(MouseEvent mouseEvent) {
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        try{
            JasperDesign load= JRXmlLoader.load(this.getClass().getResourceAsStream("/lk/ijse/royal_care_pharmacy/util/report/Blank_A4.jrxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            orderService=ServiceFactory.getInstance().getService(ServiceTypes.ORDER);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            orderDetailService=ServiceFactory.getInstance().getService(ServiceTypes.ORDERDETAIL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            customerService=ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            itemService=ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadCustomerIds();
        loadItemIds();
        try {
            setCellValueFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadOrderDate();
        loadNextOrderId();
    }
}
