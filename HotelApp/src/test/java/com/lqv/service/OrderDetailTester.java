/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.OrderDetail;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author DELL
 */
public class OrderDetailTester {
    
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
    
    @Tag("addOrderDetail")
    @ParameterizedTest
//    chỉ true khi bảng order_sell có id mới chưa liên kết khóa ngoại, vì đây là quan hệ 1:1
    @CsvSource({"42, 1/1/2000, 2/2/2000, 5, true", "44, batdau, ketthuc, 5, false", "43, batdau, ketthuc, 7, true", "41, batdau, ketthuc, 99, false"})
    public void testAddOrderDetail(int id, String timeStart, String timeEnd, int roomId, boolean expected) {
        OrderDetailService s = new OrderDetailService(conn);
        OrderDetail o = new OrderDetail();
        o.setId(id);
        o.setTimeStart(timeStart);
        o.setTimeEnd(timeEnd);
        o.setRoom_id(roomId);
        
        Assertions.assertEquals(expected, s.addOrderDetail(o));
    }
}
