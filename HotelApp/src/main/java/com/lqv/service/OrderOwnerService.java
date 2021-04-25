/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.hotelapp.App;
import com.lqv.pojo.ListOrder;
import com.lqv.pojo.OrderOwner;
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
public class OrderOwnerService {

    private Connection conn;

    public OrderOwnerService(Connection conn) {
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

    public boolean addOrderOwner(OrderOwner o) {
        boolean checkStatusRuleISNumBer = App.getListRule().get(0).isStatus();        
        if (o.getIS_Number() == null && checkStatusRuleISNumBer) {
            return false;
        }
        try {
            String sql = "INSERT INTO order_owner(id, name , phone, IS_number) VALUES(?, ?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, o.getId());
            stm.setString(2, o.getName());
            stm.setString(3, o.getPhone());
            stm.setString(4, o.getIS_Number());

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public List<ListOrder> getOrders(String kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException("error");
        }

        String sql =      "select order_owner.name, order_owner.phone, order_owner.IS_number, employee.name, order_sell.total_price, order_detail.timeStart, order_detail.timeEnd, room.name, room.quantity, category.name\n"
                        + "from order_sell\n"
                        + "inner join order_owner ON order_sell.id = order_owner.id\n"
                        + "inner join order_detail ON order_sell.id = order_detail.id\n"
                        + "inner join employee ON order_sell.employee_id = employee.id\n"
                        + "inner join room ON order_detail.room_id = room.id\n"
                        + "inner join category ON room.category_id = category.id\n"
                        + "WHERE order_owner.name like concat('%', ? , '%')";

        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<ListOrder> owners = new ArrayList<>();

        while (rs.next()) {
            ListOrder o = new ListOrder();
            o.setNameCus(rs.getString("order_owner.name"));
            o.setNameCus(rs.getString("order_owner.name"));
            o.setPhoneCus(rs.getString("order_owner.phone"));
            o.setISNumberCus(rs.getString("order_owner.IS_number"));
            o.setByEmp(rs.getString("employee.name"));
            o.setPrice(rs.getBigDecimal("order_sell.total_price"));
            o.setTimeStart(rs.getString("order_detail.timeStart"));
            o.setTimeEnd(rs.getString("order_detail.timeEnd"));
            o.setNameRoom(rs.getString("room.name"));
            o.setQuantity(rs.getInt("room.quantity"));
            o.setTypeRoom(rs.getString("category.name"));

            owners.add(o);
        }

        return owners;
    }
}
