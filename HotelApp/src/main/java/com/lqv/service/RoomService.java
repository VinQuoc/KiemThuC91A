/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.Room;
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
public class RoomService {

    private Connection conn;

    public RoomService(Connection conn) {
        this.conn = conn;
    }

    public List<Room> getRooms(String kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException("error");
        }

        String sql = "SELECT * FROM room WHERE name like concat('%', ?, '%') ORDER BY id DESC";
//        String sql = "SELECT * FROM room WHERE name == `N101`";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);

        ResultSet rs = stm.executeQuery();
        List<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            Room r = new Room();
            r.setId(rs.getInt("id"));
            r.setName(rs.getString("name"));
            r.setQuantity(rs.getInt("quantity"));
            r.setPrice(rs.getBigDecimal("price"));
            r.setImage(rs.getString("image"));
            r.setCategoryId(rs.getInt("category_id"));

            rooms.add(r);
        }

        return rooms;
    }

    public boolean addRoom(Room p) {
        try {
//            String sql = "INSERT INTO room(name, price, category_id) VALUES(?, ?, ?)";
            String sql = "INSERT INTO room(name, quantity, price, category_id) VALUES(?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, p.getName());
            stm.setInt(2, p.getQuantity());
            stm.setBigDecimal(3, p.getPrice());
            stm.setInt(4, p.getCategoryId());

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean deleteProduct(int roomid) {
        try {
            String sql = "DELETE FROM room WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, roomid);

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
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

}
