/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.ListOrder;
import com.lqv.service.JdbcUtils;
import com.lqv.service.OrderOwnerService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class OrderViewController implements Initializable {

    @FXML
    private void switchToManagement() throws IOException {
        App.setRoot("management");
    }

    @FXML
    private void switchToRooms() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToRule() throws IOException {
        App.setRoot("rule");
    }

    @FXML
    private void switchToOrderView() throws IOException {
        App.setRoot("orderView");
    }

    @FXML
    private void logOut() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private TableView<ListOrder> tbOrders;
    @FXML
    private TextField txtKeywords;

    @FXML
    private Text textRole;
    @FXML
    private Text textNameEmp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumns();
        loadData("");

        this.txtKeywords.textProperty().addListener((obj) -> {
            loadData(this.txtKeywords.getText());
        });

        this.textRole.setText(App.getEmp().getRole());
        this.textNameEmp.setText(App.getEmp().getName());
    }

    private void loadColumns() {

        TableColumn colNameCus = new TableColumn("Tên khách hàng");
        colNameCus.setPrefWidth(150);
        colNameCus.setCellValueFactory(new PropertyValueFactory("nameCus"));

        TableColumn colPhone = new TableColumn("Sdt liên hệ");
        colPhone.setPrefWidth(90);
        colPhone.setCellValueFactory(new PropertyValueFactory("phoneCus"));

        TableColumn colISNumber = new TableColumn("Mã căn cước");
        colISNumber.setPrefWidth(90);
        colISNumber.setCellValueFactory(new PropertyValueFactory("ISNumberCus"));

        TableColumn colByEmp = new TableColumn("Nhân viên thực hiện");
        colByEmp.setPrefWidth(150);
        colByEmp.setCellValueFactory(new PropertyValueFactory("byEmp"));

        TableColumn colRoom = new TableColumn("Phòng");
        colRoom.setPrefWidth(90);
        colRoom.setCellValueFactory(new PropertyValueFactory("nameRoom"));

        TableColumn colTypeRoom = new TableColumn("Loại phòng");
        colTypeRoom.setPrefWidth(90);
        colTypeRoom.setCellValueFactory(new PropertyValueFactory("typeRoom"));

        TableColumn colQuantity = new TableColumn("Số lượng giường");
        colQuantity.setPrefWidth(90);
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn colTimeStart = new TableColumn("Ngày nhận phòng");
        colTimeStart.setPrefWidth(90);
        colTimeStart.setCellValueFactory(new PropertyValueFactory("timeStart"));

        TableColumn colTimeEnd = new TableColumn("Ngày trả phòng");
        colTimeEnd.setPrefWidth(90);
        colTimeEnd.setCellValueFactory(new PropertyValueFactory("timeEnd"));

        TableColumn colPrice = new TableColumn("Thành tiền");
        colPrice.setPrefWidth(90);
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        this.tbOrders.getColumns().addAll(colNameCus, colPhone, colISNumber, colByEmp, colRoom, colTypeRoom, colQuantity, colTimeStart, colTimeEnd, colPrice);

    }

    private void loadData(String kw) {
        try {
            Connection conn = JdbcUtils.getConn();
            OrderOwnerService o = new OrderOwnerService(conn);
            List<ListOrder> orders = o.getOrders(kw);
            this.tbOrders.setItems(FXCollections.observableList(orders));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
