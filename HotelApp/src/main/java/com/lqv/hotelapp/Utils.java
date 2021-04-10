package com.lqv.hotelapp;

import javafx.scene.control.Alert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Utils {
    public static Alert getAlertBox(String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setContentText(content);
        
        return a;
    }
}
