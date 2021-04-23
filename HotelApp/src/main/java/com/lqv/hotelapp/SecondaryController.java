package com.lqv.hotelapp;

import com.lqv.pojo.Category;
import com.lqv.pojo.Room;
import com.lqv.service.CategoryService;
import com.lqv.service.JdbcUtils;
import com.lqv.service.OrderSellService;
import com.lqv.service.RoomService;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SecondaryController implements Initializable {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToInfoCus() throws IOException {
        App.setRoot("infoCus");

    }

    @FXML
    private ComboBox<Category> cbCategories;
    @FXML
    private TableView<Room> tbRooms;
    @FXML
    private TextField txtKeywords;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CategoryService s = new CategoryService();
            List<Category> cates = s.getCates("");

            this.cbCategories.setItems(FXCollections.observableList(cates));
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadColumns();
        loadData("");

        this.txtKeywords.textProperty().addListener((obj) -> {
            loadData(this.txtKeywords.getText());
        });

        this.tbRooms.setRowFactory(obj -> {
            TableRow row = new TableRow();

            row.setOnMouseClicked(evt -> {
                try {
                    Room r = this.tbRooms.getSelectionModel().getSelectedItem();
                    txtName.setText(r.getName());
                    txtPrice.setText(r.getPrice().toString());

                    Category c = new CategoryService().getCateById(r.getCategoryId());

                    this.cbCategories.getSelectionModel().select(c);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return row;
        });
    }

    public void addRoom() {
        Room r = new Room();
        r.setName(txtName.getText());
        r.setPrice(new BigDecimal(txtPrice.getText()));
        r.setQuantity(Integer.parseInt(txtQuantity.getText()));
        Category c = this.cbCategories.getSelectionModel().getSelectedItem();
        r.setCategoryId(c.getId());

        Connection conn;
        try {
            conn = JdbcUtils.getConn();

            RoomService s = new RoomService(conn);
            if (s.addRoom(r) == true) {
                Utils.getAlertBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                loadData("");
            } else {
                Utils.getAlertBox("FAILED", Alert.AlertType.WARNING).show();
            }

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadColumns() {
        TableColumn colId = new TableColumn("Mã phòng");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Tên phòng");
        colName.setPrefWidth(90);
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colQuantity = new TableColumn("Số lượng giường");
        colQuantity.setPrefWidth(120);
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn colPrice = new TableColumn("Gía thuê phòng");
        colPrice.setPrefWidth(100);
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

//        --------------------------------
        TableColumn colRent = new TableColumn();
        colRent.setCellFactory((obj) -> {
            Button btn = new Button("Thuê");
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
                                    Room p = (Room) cell.getTableRow().getItem();

                                    try ( Connection conn = JdbcUtils.getConn()) {
                                        RoomService s = new RoomService(conn);
                                        if (s.deleteProduct(p.getId()) == true) {
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

        this.tbRooms.getColumns().addAll(colId, colName, colQuantity, colPrice, colAction, colRent);
    }

    private void loadData(String kw) {
        try {
            Connection conn = JdbcUtils.getConn();
            RoomService r = new RoomService(conn);
            List<Room> rooms = r.getRooms(kw);
            this.tbRooms.setItems(FXCollections.observableList(rooms));
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleChoose(ActionEvent evt) {
        Room r = new Room();
        TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
        Room room = (Room) cell.getTableRow().getItem();

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("infoCus.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        InfoCusController display = Loader.getController();

//      Lấy tên phòng truyền vào txtRoom
        display.setRoom(room.getName());

//      Lấy thông tin object room
        display.getRoom(room);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();

        System.out.println(room);
    }

}
