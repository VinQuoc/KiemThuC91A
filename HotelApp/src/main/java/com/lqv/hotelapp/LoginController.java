/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.service.AccountService;
import com.lqv.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
    private void switchToOrderView() throws IOException {
        App.setRoot("orderView");
    }

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

//    public void login(ActionEvent evt) {
//        try {            
//            Connection conn = JdbcUtils.getConn();
//            AccountService s = new AccountService(conn);            
//            if (s.checkAcc(txtUserName.getText(), txtPassword.getText())) {
//                Utils.getAlertBox("Đăng nhập thành công", Alert.AlertType.INFORMATION).show();
//            }  else {
//                Utils.getAlertBox("Đăng nhập thất bại", Alert.AlertType.WARNING).show();
//            }
//                
//        } catch (SQLException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void login(ActionEvent evt) throws IOException {
        try {
            Connection conn = JdbcUtils.getConn();
            AccountService s = new AccountService(conn);
            if (s.checkAcc(txtUserName.getText(), txtPassword.getText())) {
//                Utils.getAlertBox("Đăng nhập thành công", Alert.AlertType.INFORMATION).show();
                App.setRoot("orderView");
            } else {
                Utils.getAlertBox("Đăng nhập thất bại", Alert.AlertType.WARNING).show();
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
