/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.hotelapp.App;
import com.lqv.pojo.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class OrderDetailService {

    private Connection conn;

    public OrderDetailService(Connection conn) {
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
    
    public boolean addOrderDetail(OrderDetail o) {         
        try {
            String sql = "INSERT INTO order_detail(id, timeStart , timeEnd, room_id) VALUES(?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, o.getId());
            stm.setString(2, o.getTimeStart());
            stm.setString(3, o.getTimeEnd());
            stm.setInt(4, o.getRoom_id());

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
}
