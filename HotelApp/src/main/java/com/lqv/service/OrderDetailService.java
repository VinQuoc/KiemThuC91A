/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.hotelapp.App;
import com.lqv.pojo.OrderDetail;
import static java.lang.Integer.parseInt;
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
        if (!checkTimeStart(o.getTimeStart())) {
            return false;
        }
       
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
    
    public boolean checkTimeStart(String time) {
        String s = java.time.LocalDate.now().toString();
        String now[] =  s.split("-");
        
        String[] day = time.split("-");
        if (parseInt(day[0]) < parseInt(now[0])) {
            return false;
        }
        if (parseInt(day[1]) < parseInt(now[1])) {
            return false;
        }
        if (parseInt(day[2]) < parseInt(now[2])) {
            return false;
        }
        System.out.println("ngay hien tai: " + now[0] + " " + now[1] + " " + now[2]);
        System.out.println("ngay bat dau cho thue: " + day[0] + " " + day[1] + " " + day[2]);
        return true;
    }
    
    public boolean checkTimeEnd(String end, String rent) {
        String endO[] =  end.split("-");
        
        String[] day = rent.split("-");
        if (parseInt(day[0]) < parseInt(endO[0])) {
            return false;
        }
        if (parseInt(day[1]) < parseInt(endO[1])) {
            return false;
        }
        if (parseInt(day[2]) < parseInt(endO[2])) {
            return false;
        }
        System.out.println("ngay hien tai: " + endO[0] + " " + endO[1] + " " + endO[2]);
        System.out.println("ngay bat dau cho thue: " + day[0] + " " + day[1] + " " + day[2]);
        return true;
    }
    
}
