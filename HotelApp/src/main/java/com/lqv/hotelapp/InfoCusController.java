/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.OrderSell;
import com.lqv.pojo.OrderDetail;
import com.lqv.pojo.OrderOwner;
import com.lqv.pojo.Room;
import com.lqv.service.JdbcUtils;
import com.lqv.service.OrderDetailService;
import com.lqv.service.OrderOwnerService;
import com.lqv.service.OrderSellService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class InfoCusController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCMND;
    @FXML
    private TextField txtDT;

    @FXML
    private TextField txtRoom;
    @FXML
    private TextField txtTien;

    @FXML
    private DatePicker dtStart;
    @FXML
    private DatePicker dtEnd;

    private Room room = new Room();

    private int oId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.txtName.setText(App.getEmp().getName());
        System.out.println("Da thiet lap trang infoCus");
//        System.out.println(App.getEmp().getName());
    }

    public int getDayStart() {
        LocalDate date = dtStart.getValue();
//        System.out.println("day start " + date.getDayOfYear());
        return date.getDayOfYear();
    }

    public void getDayEnd() {
        LocalDate date = dtEnd.getValue();
        BigDecimal day = new BigDecimal(date.getDayOfYear() - this.getDayStart());
        BigDecimal price = day.multiply(room.getPrice());
        String s = String.valueOf(price);
        this.txtTien.setText(s);
    }

    public void setRoom(String name) {
        this.txtRoom.setText(name);
    }

    public void getRoom(Room r) {
        room.setId(r.getId());
        room.setImage(r.getImage());
        room.setName(r.getName());
        room.setPrice(r.getPrice());
        room.setQuantity(r.getQuantity());
        room.setCategoryId(r.getCategoryId());
    }

    public void addOrderOwner() {

        OrderSell order = new OrderSell();
        order.setTotal_price(new BigDecimal(txtTien.getText()));
        order.setPay_status(true);
        order.setEmployeeId(App.getEmp().getId());
        
        
        Connection conn;
        try {
            conn = JdbcUtils.getConn();

            OrderSellService orderS = new OrderSellService(conn);
            OrderDetailService detailS = new OrderDetailService(conn);
            OrderOwnerService ownerS = new OrderOwnerService(conn);

            if (orderS.addOrder(order) == true) {

                OrderDetail detail = new OrderDetail();
                detail.setId(orderS.getOrderByIDLast());
                LocalDate dayEnd = this.dtEnd.getValue();
                detail.setTimeEnd(dayEnd.toString());
                LocalDate dayStart = this.dtEnd.getValue();
                detail.setTimeStart(dayStart.toString());
                detail.setRoom_id(room.getId());

                OrderOwner owner = new OrderOwner();
                owner.setId(orderS.getOrderByIDLast());
                owner.setName(this.txtName.getText());
                owner.setPhone(this.txtDT.getText());
                owner.setIS_Number(this.txtCMND.getText());

                if (detailS.addOrderDetail(detail) == true && ownerS.addOrderOwner(owner) == true) {
                    Utils.getAlertBox("SUCCESSFUL order", Alert.AlertType.INFORMATION).show();
                } else {
                    Utils.getAlertBox("FAILED ", Alert.AlertType.WARNING).show();
                }
            } else {
                Utils.getAlertBox("FAILED order ", Alert.AlertType.WARNING).show();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(InfoCusController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
