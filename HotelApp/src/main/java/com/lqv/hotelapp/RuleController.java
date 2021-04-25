/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.textRole.setText(App.getEmp().getRole());
        this.textNameEmp.setText(App.getEmp().getName());
    }

}
