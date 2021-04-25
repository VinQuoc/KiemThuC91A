/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.hotelapp.App;
import com.lqv.pojo.OrderSell;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class OrderSellService {

    private Connection conn;

    public OrderSellService(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public boolean addOrder(OrderSell o) {
        BigDecimal so = new BigDecimal(0);
        boolean checkStatusRuleTimeRent = App.getListRule().get(2).isStatus();  
        if (o.getTotal_price().compareTo(so) == -1 && checkStatusRuleTimeRent) {
            return false;
        }
        try {
            String sql = "INSERT INTO order_sell(total_price, pay_status, employee_id) VALUES(?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setBigDecimal(1, o.getTotal_price());
            stm.setBoolean(2, o.isPay_status());
            stm.setInt(3, o.getEmployeeId());

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(OrderSellService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public int getOrderByIDLast() throws SQLException {
        int result = 0;
        Connection conn = JdbcUtils.getConn();

        String sql = "SELECT * FROM order_sell ORDER BY id DESC LIMIT 1";
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            result = rs.getInt("id");
        }
        return result;
    }

}
