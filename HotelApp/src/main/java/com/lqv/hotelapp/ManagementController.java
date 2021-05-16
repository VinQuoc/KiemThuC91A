/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.Employee;
import com.lqv.service.EmployeeService;
import com.lqv.service.JdbcUtils;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ManagementController implements Initializable {

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
    private Text textRole;
    @FXML
    private Text textNameEmp;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtMail;
    @FXML
    private TableView tbEmployee;
    @FXML
    private TextField txtKeywords;

    private int empId;
    private String error;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        Hiển thị vai trò của người dùng hiện thời
        this.textRole.setText(App.getEmp().getRole());
        this.textNameEmp.setText(App.getEmp().getName());

//        Lấy dữ liệu khi tải scene
        loadColumns();
        loadData("");

//        Cập nhập TableView khi gõ từ khóa tên nhân viên
        this.txtKeywords.textProperty().addListener((obj) -> {
            loadData(this.txtKeywords.getText());
        });

//         Lấy giá trị Object room khi click chọn
        this.tbEmployee.setRowFactory(obj -> {
            TableRow row = new TableRow();

            row.setOnMouseClicked(evt -> {
                Employee e = (Employee) this.tbEmployee.getSelectionModel().getSelectedItem();

                if (e.getPhone() == null) {
                    txtPhone.setText("");
                } else {
                    txtPhone.setText(e.getPhone().toString());
                }
                if (e.getEmail() == null) {
                    txtMail.setText("");
                } else {
                    txtMail.setText(e.getEmail().toString());

                }
                txtName.setText(e.getName());
                empId = e.getId();
            });
            return row;
        });
    }

    private void loadColumns() {
        TableColumn colId = new TableColumn("Mã nhân viên");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Tên nhân viên");
        colName.setPrefWidth(90);
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colPhone = new TableColumn("Số điện thoại");
        colPhone.setPrefWidth(90);
        colPhone.setCellValueFactory(new PropertyValueFactory("phone"));

        TableColumn colEmail = new TableColumn("Email");
        colEmail.setPrefWidth(120);
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        TableColumn colRole = new TableColumn("Chức vụ");
        colRole.setPrefWidth(100);
        colRole.setCellValueFactory(new PropertyValueFactory("role"));

        TableColumn colUser = new TableColumn("Tài khoản");
        colUser.setPrefWidth(100);
        colUser.setCellValueFactory(new PropertyValueFactory("username"));

//        --------------------------------
        TableColumn colAcc = new TableColumn();
        colAcc.setCellFactory((obj) -> {
            Button btn = new Button("Tạo tài khoản");

            btn.setOnAction(evt -> {
                this.handleChoose(evt);
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

//        //      -------------------------------
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory((obj) -> {
            Button btn = new Button("Xóa");

            btn.setOnAction(evt -> {
                Utils.getAlertBox("Bạn chắc chắn xóa không?", Alert.AlertType.CONFIRMATION)
                        .showAndWait().ifPresent(bt -> {
                            if (bt == ButtonType.OK) {
                                try {
                                    TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                                    Employee e = (Employee) cell.getTableRow().getItem();

                                    try ( Connection conn = JdbcUtils.getConn()) {
                                        EmployeeService s = new EmployeeService(conn);
                                        if (s.deleteEmp(e.getId()) == true) {
                                            Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                            this.loadData("");
                                        } else {
                                            Utils.getAlertBox("FAILED", Alert.AlertType.ERROR).show();
                                        }
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
            });

            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        this.tbEmployee.getColumns().addAll(colId, colName, colPhone, colEmail, colRole, colAcc, colUser, colAction);
    }

    private void loadData(String kw) {
        try {
            Connection conn = JdbcUtils.getConn();
            EmployeeService e = new EmployeeService(conn);
            List<Employee> emps = e.getEmployees(kw);
            this.tbEmployee.setItems(FXCollections.observableList(emps));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEmp() {
        Employee e = new Employee();
        e.setName(txtName.getText());
        e.setPhone(txtPhone.getText());
        e.setEmail(txtMail.getText());

        if (e.getPhone().length() < 1) {
            error = "Bạn phải nhập số điện thoại";
        }
        if (e.getName().length() < 1) {
            error = "Bạn phải nhập tên nhân viên";
        }

        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            EmployeeService s = new EmployeeService(conn);
            if (s.addEmp(e) == true) {
                Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                loadData("");
            } else {
                Utils.getAlertBox(error, Alert.AlertType.WARNING).show();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateEmp() {
        Employee e = new Employee();
        e.setName(txtName.getText());
        e.setPhone(txtPhone.getText());
        e.setEmail(txtMail.getText());

//        điều kiện tạo hồ sơ cho nhân viên
        if (e.getPhone().length() < 1) {
            error = "Bạn phải nhập số điện thoại";
        }
        if (e.getName().length() < 1) {
            error = "Bạn phải nhập tên nhân viên";
        }

//        điều kiện chỉnh sửa Account cho nhân viên (chỉ nhân viên đã có hồ sơ trước đó mới được tạo tài khoản)

        Connection conn;
        try {
            conn = JdbcUtils.getConn();

            EmployeeService s = new EmployeeService(conn);
            if (s.updateEmp(e, empId) == true) {
                Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                loadData("");
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
    private void handleChoose(ActionEvent evt) {
        Employee e = new Employee();
        TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
        Employee emp = (Employee) cell.getTableRow().getItem();

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("createAcc.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CreateAccController display = Loader.getController();

//      Lấy tên phòng truyền vào txtRoom
        display.setNameEmp(emp.getName());

//      Lấy thông tin object room
        display.getEmpId(emp);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        
//        thao tác xong sẽ load lại trang
        stage.showAndWait();        
        loadData("");
    }

}
