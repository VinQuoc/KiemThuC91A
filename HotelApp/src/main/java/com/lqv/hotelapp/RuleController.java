/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import com.lqv.pojo.SystemRule;
import com.lqv.service.JdbcUtils;
import com.lqv.service.SystemRuleService;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class RuleController implements Initializable {

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
    private TextField txtRule;
    @FXML
    private TextArea txtDes;
    @FXML
    private RadioButton rbtnStatus;
    @FXML
    private TableView tbRule;

    private String error;
    private int ruleId = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        Hiển thị vai trò của người dùng hiện thời
        this.textRole.setText(App.getEmp().getRole());
        this.textNameEmp.setText(App.getEmp().getName());

//        Lấy dữ liệu khi tải scene
        loadColumns();
        loadData();

//        Lấy giá trị Object rule khi click chọn
        this.tbRule.setRowFactory(obj -> {
            TableRow row = new TableRow();

            row.setOnMouseClicked(evt -> {
                SystemRule r = (SystemRule) this.tbRule.getSelectionModel().getSelectedItem();

                if (r.getDescription() == null) {
                    txtDes.setText("");
                } else {
                    txtDes.setText(r.getDescription().toString());

                }
                txtRule.setText(r.getRule().toString());
                ruleId = r.getId();
                rbtnStatus.setSelected(r.isStatus());
                txtRule.setDisable(true);
            });
            return row;
        });
    }

    private void loadColumns() {
        TableColumn colId = new TableColumn("Mã yêu cầu");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colRule = new TableColumn("Yêu cầu");
        colRule.setPrefWidth(120);
        colRule.setCellValueFactory(new PropertyValueFactory("rule"));

        TableColumn colDes = new TableColumn("Mô tả");
        colDes.setPrefWidth(200);
        colDes.setCellValueFactory(new PropertyValueFactory("description"));

        TableColumn colStatus = new TableColumn("Trạng thái");
        colStatus.setPrefWidth(120);
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));

        TableColumn colUse = new TableColumn();
        colUse.setCellFactory((obj) -> {
            Button btn = new Button("Thay đổi");
            btn.setOnAction(evt -> {
                this.handleChoose(evt);
            });
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

//        ---------------------------
//        colUse.setCellFactory((obj) -> {
//            String btnText1 = "Sử dụng";
//            String btnText2 = "Không sử dụng";
//            Button btn = new Button();
//
//            if (colStatus.getCellFactory().equals("false")) {
//                btn.setText(btnText1);
//                btn.setOnAction(evt -> {
//                    this.handleChoose(evt);
//                    btn.setText(btnText2);
//                });
//            } else {
//                btn.setText(btnText2);
//                btn.setOnAction(evt -> {
//                    this.handleChoose(evt);
//                    btn.setText(btnText1);
//                });
//            }
//            
//            System.out.println("Gia tri: " + colStatus.getText());
//
//            TableCell cell = new TableCell();
//            cell.setGraphic(btn);
//            return cell;
//        });
//        ---------------------
//        TableColumn colAction = new TableColumn();
//        colAction.setCellFactory((obj) -> {
//            Button btn = new Button("Xóa");
//
//            btn.setOnAction(evt -> {
//                Utils.getAlertBox("Bạn chắc chắn xóa không?", Alert.AlertType.CONFIRMATION)
//                        .showAndWait().ifPresent(bt -> {
//                            if (bt == ButtonType.OK) {
//                                try {
//                                    TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
//                                    SystemRule e = (SystemRule) cell.getTableRow().getItem();
//
//                                    try ( Connection conn = JdbcUtils.getConn()) {
//                                        SystemRuleService s = new SystemRuleService(conn);
//                                        if (s.deleteRule(e.getId()) == true) {
//                                            Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
//                                            this.loadData();
//                                        } else {
//                                            Utils.getAlertBox("FAILED", Alert.AlertType.ERROR).show();
//                                        }
//                                    }
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
//                        });
//            });
//            TableCell cell = new TableCell();
//            cell.setGraphic(btn);
//            return cell;
//        });
        this.tbRule.getColumns().addAll(colId, colRule, colDes, colStatus, colUse);
    }

    private void loadData() {
        try {
            Connection conn = JdbcUtils.getConn();
            SystemRuleService s = new SystemRuleService(conn);
            List<SystemRule> rules = s.getSystemRules();
            this.tbRule.setItems(FXCollections.observableList(rules));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtRule.setDisable(false);
    }

    public void addRule() {
        SystemRule r = new SystemRule();
        r.setRule(txtRule.getText());
        r.setDescription(txtDes.getText());
        r.setStatus(rbtnStatus.isSelected());

        if (r.getRule().length() < 1) {
            error = "Bạn phải nhập quy định";
        }
        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            SystemRuleService s = new SystemRuleService(conn);
            if (s.addRule(r) == true) {
                Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                loadData();
            } else {
                Utils.getAlertBox(error, Alert.AlertType.WARNING).show();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateRule() {
        SystemRule r = new SystemRule();
        r.setRule(txtRule.getText());
        r.setDescription(txtDes.getText());
        r.setStatus(rbtnStatus.isSelected());

        if (r.getRule().length() < 1) {
            error = "Bạn phải nhập quy định";
        }

        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            SystemRuleService s = new SystemRuleService(conn);
            if (s.updateRule(r, ruleId) == true) {
                Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                loadData();
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

        TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
        SystemRule rule = (SystemRule) cell.getTableRow().getItem();
        rule.setStatus(!rule.isStatus());

        Connection conn;
        try {
            conn = JdbcUtils.getConn();
            SystemRuleService s = new SystemRuleService(conn);
            s.updateRule(rule, rule.getId());

            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(RuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadData();
        txtRule.setDisable(true);
    }

    public void Loading() {
        loadData();
        txtRule.setText("");
        txtDes.setText("");
        rbtnStatus.setSelected(false);
        ruleId = 0;
    }
}
