/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.Employee;
import com.lqv.service.EmployeeService;
import com.lqv.service.JdbcUtils;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class CreateAccController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtCheckPass;
    @FXML
    private Text txtName;
    @FXML
    private Button btnDelete;

    private int empId;
    private String error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void getEmpId(Employee e) {
        empId = (e.getId());
    }

    public void setEmp(Employee e) {
        this.txtName.setText(e.getName());
        this.txtUsername.setText(e.getUsername());
        if (this.txtUsername.getText() == null || this.txtUsername.getText() == "") {
            this.txtUsername.setDisable(false);
            this.btnDelete.setVisible(false);
        }
    }

    @FXML
    private void updateEmpAcc(ActionEvent event) {

        Employee e = new Employee();
        e.setUsername(txtUsername.getText());
        e.setPassword(txtPassword.getText());

//        điều kiện chỉnh sửa Account cho nhân viên (chỉ nhân viên đã có hồ sơ trước đó mới được tạo tài khoản)        
        if (e.getPassword().length() < 1) {
            error = "Bạn phải nhập mật khẩu";
        }
        if (e.getPassword().compareTo(txtCheckPass.getText()) != 0) {
            error = "Nhập lại mật khẩu không đúng";
            e.setPassword("");
        }
        if (txtUsername.getText() == null) {
            error = "Bạn phải nhập tên đăng nhập";
        }
        Connection conn;
        try {
            conn = JdbcUtils.getConn();

            EmployeeService s = new EmployeeService(conn);
            if (s.updateEmpAcc(e, empId) == true) {
                Utils.getAlertBox("Bạn đã đổi mật khẩu thành công", Alert.AlertType.CONFIRMATION)
                        .showAndWait().ifPresent(bt -> {
                            if (bt == ButtonType.OK) {
                                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

                            }
                        });
            } else {
                Utils.getAlertBox(error, Alert.AlertType.WARNING).show();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("bao loi: " + error);
        error = "";
    }

    @FXML
    private void deleteAccEmp(ActionEvent event) {
        this.txtUsername.setText("");
        Connection conn;
        try {
            conn = JdbcUtils.getConn();

            EmployeeService s = new EmployeeService(conn);
            if (s.deleteAccEmp(empId) == true) {
                Utils.getAlertBox("Xóa tài khoản nhân viên thành công", Alert.AlertType.CONFIRMATION)
                        .showAndWait().ifPresent(bt -> {
                            if (bt == ButtonType.OK) {
                                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

                            }
                        });
            } else {
                Utils.getAlertBox("Thao tác thất bại", Alert.AlertType.INFORMATION).show();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
