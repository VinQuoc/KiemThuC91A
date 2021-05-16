/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.Employee;
import com.lqv.service.EmployeeService;
import com.lqv.service.JdbcUtils;
import com.lqv.service.SystemRuleService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private PasswordField txtPassword;
    @FXML
    private Text txtError;

    public String error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void login(ActionEvent evt) throws IOException {
        try {
            Connection conn = JdbcUtils.getConn();
            EmployeeService emp = new EmployeeService(conn);
            Employee e = new Employee();
            SystemRuleService ruleS = new SystemRuleService(conn);           

//            Đăng nhập thành công thì chuyển trang
            if (emp.checkAcc(txtUserName.getText(), txtPassword.getText(), e)) {
                emp.getEmp(txtUserName.getText(), txtPassword.getText(), e);
                App.setEmp(e);
                App.setSystemRules(ruleS.getSystemRules());
                App.setRoot("orderView");
            } else {
//                Utils.getAlertBox("Đăng nhập thất bại", Alert.AlertType.WARNING).show();
                this.txtError.setText(e.getError());
                this.txtError.setVisible(true);
                System.out.println("bao loi: " + e.getError());
                System.out.println("username: " + e.getUsername());
                System.out.println("password: " + e.getPassword());
                e.setError("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
