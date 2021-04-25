/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.ListOrder;
import com.lqv.pojo.OrderOwner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author DELL
 */
public class OrderOwnerTester {
        
    private static Connection conn;

    @BeforeAll
    public static void setUpClass() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(RoomTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RoomTester.class.getName()).log(Level.SEVERE, null, ex);
        };
    }
    
    @Tag("addOrderOwner")
    @ParameterizedTest
//    chỉ true khi bảng order_sell có id mới chưa liên kết khóa ngoại, vì đây là quan hệ 1:1
    @CsvSource({"42, ten42, phone42, isnumber42, true", "41, id_daCoKhoaNgoai, phone41, isnumber41, false", })
    public void testAddOrderDetail(int id, String name, String phone, String isnumber, boolean expected) {
        OrderOwnerService s = new OrderOwnerService(conn);
        OrderOwner o = new OrderOwner();
        o.setId(id);
        o.setName(name);
        o.setPhone(phone);
        o.setIS_Number(isnumber);
        
        Assertions.assertEquals(expected, s.addOrderOwner(o));
    }
    
    @Test
    public void testWithKeyword() throws SQLException {
        OrderOwnerService s = new OrderOwnerService(conn);
        List<ListOrder> rooms = s.getOrders("");

        rooms.forEach(p -> {
            Assertions.assertTrue(p.getNameCus().contains(""));
        });
    }
    
       @Test
    public void testWithUnknowKeyword() throws SQLException {
        OrderOwnerService s = new OrderOwnerService(conn);
        List<ListOrder> products = s.getOrders("273627uiesdsjd#$%^");

        Assertions.assertEquals(0, products.size());
    }
}
