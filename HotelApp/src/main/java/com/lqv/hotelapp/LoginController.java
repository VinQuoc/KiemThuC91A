/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.Account;
import com.lqv.pojo.Room;
import com.lqv.service.AccountService;
import com.lqv.service.JdbcUtils;
import com.lqv.service.RoomService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void login(ActionEvent evt) {
        try {            
            Connection conn = JdbcUtils.getConn();
            AccountService s = new AccountService(conn);
            
//            System.out.println(txtUserName.getText());
            
            if (s.checkAcc(txtUserName.getText(), txtPassword.getText())) {
                Utils.getAlertBox("Đăng nhập thành công", Alert.AlertType.INFORMATION).show();
            }  else {
                Utils.getAlertBox("Đăng nhập thất bại", Alert.AlertType.WARNING).show();
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public boolean checkAcc(String user, String pass) {
//        try {
//            Connection conn = JdbcUtils.getConn();
//            RoomService s = new RoomService(conn);
//            List<Account> acc = s.getRooms(kw);
//            this.tbRooms.setItems(FXCollections.observableList(rooms));
//
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return false;
//    }
    
}
