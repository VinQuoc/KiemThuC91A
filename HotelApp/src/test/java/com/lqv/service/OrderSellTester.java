/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.OrderSell;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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
public class OrderSellTester {

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
//    cần tạo order_sell mới để test add của owner, detail vì ở đây là quan hệ 1:1
    @CsvSource({"555555, 1, 1, true", "123133, 1, 4, false"})
    public void testAddOrder(BigDecimal price, boolean pay_status, int empId, boolean expected) {
        OrderSellService s = new OrderSellService(conn);
        OrderSell o = new OrderSell();
        o.setTotal_price(price);
        o.setPay_status(pay_status);
        o.setEmployeeId(empId);
        Assertions.assertEquals(expected, s.addOrder(o));
    }
    
    @Test
    public void testGetOrderByIDLast() {
        try {
            OrderSellService s = new OrderSellService(conn);
            int id = 1;
            boolean check;
            if (s.getOrderByIDLast() > 0) {
                check = true;
            } else {
                check = false;
            }
            Assertions.assertTrue(check);
        } catch (SQLException ex) {
            Logger.getLogger(OrderSellTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
