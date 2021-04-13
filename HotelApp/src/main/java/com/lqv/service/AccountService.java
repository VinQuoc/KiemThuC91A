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
        if (user == null) {
            throw new SQLDataException("error");
        }

        try {
            String sql = "SELECT * FROM account WHERE username == ? And password == ?";
            PreparedStatement stm = this.getConn().prepareStatement(sql);
            stm.setString(1, user);
            stm.setString(2, pass);
            ResultSet rs = stm.executeQuery();
            
            if (rs != null)
                return true;
            else 
                return false;

//            while (rs.next()) {
//                if (rs.getString("user_emp") == user) {
//                    if (rs.getString("password") == pass) {
//                        return true;
//                    }                        
//                    else return false;
//                } else {
//                    return false;
//                }
//            }
//            return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
