/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class AccountService {

    private Connection conn;

    public AccountService(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

//    ----------------------------------------------------
    public boolean checkAcc(String user, String pass) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM acc_emp WHERE username = ? AND password = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, user);
        stm.setString(2, pass);

        ResultSet rs = stm.executeQuery();
//        System.out.println(rs);

        String username = "";
        String password = "";
        while (rs.next()) {
//            System.out.println(rs.getString("username"));
//            System.out.println(rs.getString("password"));
            username = rs.getString("username");
            password = rs.getString("password");
        }

//        System.out.println(username);
//        System.out.println(password);
        
        if (username != "" && password != "") {
            return true;
        }        
        return false;
    }
}
