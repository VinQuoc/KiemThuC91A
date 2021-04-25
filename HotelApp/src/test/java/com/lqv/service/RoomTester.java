/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.Room;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author DELL
 */
public class RoomTester {

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

    @Test
    public void testWithKeyword() throws SQLException {
        RoomService s = new RoomService(conn);
        List<Room> rooms = s.getRooms("N101");

        rooms.forEach(p -> {
            Assertions.assertTrue(p.getName().contains("N101"));
        });
    }

    @Test
    public void testWithUnknowKeyword() throws SQLException {
        RoomService s = new RoomService(conn);
        List<Room> products = s.getRooms("273627uiesdsjd#$%^");

        Assertions.assertEquals(0, products.size());
    }

    @Test
    public void testException() throws SQLException {
        Assertions.assertThrows(SQLDataException.class, () -> {
            RoomService s = new RoomService(conn);
            List<Room> products = s.getRooms(null);
        });
    }

    @Test
    @DisplayName("...")
    @Tag("add-room")
    public void testAddProductWithInvalidCateId() {
        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName("ABC");
        o.setPrice(new BigDecimal(100));
        o.setQuantity(5);
        o.setCategoryId(999);

        Assertions.assertFalse(s.addRoom(o));
    }

    @Test
    @DisplayName("...")
    @Tag("add-room")
    public void testAddProductWithInvalidName() {
        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName(null);
        o.setQuantity(5);
        o.setPrice(new BigDecimal(100));
        o.setCategoryId(1);

        Assertions.assertFalse(s.addRoom(o));
    }

    @Test
    @DisplayName("...")
    @Tag("add-room")
    public void testAddProduct() {
        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName("ABC");
        o.setQuantity(5);
        o.setPrice(new BigDecimal(99999));
        o.setCategoryId(2);

        Assertions.assertTrue(s.addRoom(o));
    }

    @ParameterizedTest
    @CsvSource({"A111,111111,3,9999,false", ",995555,5,9999,false", "A222,998888,2,1,true"})
    public void testDataSource(String name, int quantity, BigDecimal price, int cateId, boolean expected) {
        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName(name);
        o.setPrice(price);
        o.setQuantity(quantity);
        o.setCategoryId(cateId);

        Assertions.assertEquals(expected, s.addRoom(o));
    }

    @Test
    @DisplayName("...")
    @Tag("getRoomById")
    public void testGetRoomByIdWithInvalidId() {
        boolean check;
        try {
            RoomService s = new RoomService(conn);

            if (s.getRoomByID(99) != null) {
                check = true;
            } else {
                check = false;
            }

            Assertions.assertFalse(check);
        } catch (SQLException ex) {
            Logger.getLogger(RoomTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("...")
    @Tag("getRoomById")
    public void testGetRoomByIdWithValidId() {
        boolean check;
        try {
            RoomService s = new RoomService(conn);

            if (s.getRoomByID(7) != null) {
                check = true;
            } else {
                check = false;
            }

            Assertions.assertTrue(check);
        } catch (SQLException ex) {
            Logger.getLogger(RoomTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @DisplayName("...")
    @Tag("upp-room")
    public void testUpdateRoomWithValidId() {
        //          Thay giá trị id bất kì hợp lệ ở đây
        int roomId = 25;
        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName("CCCC");
        o.setQuantity(3);
        o.setPrice(new BigDecimal(99119));
        o.setCategoryId(1);

        Assertions.assertTrue(s.updateRoom(o, roomId));
    }

    @Test
    @DisplayName("...")
    @Tag("upp-room")
    public void testUpdateRoomWithInValidId() {
        //          Thay giá trị id bất kì hợp lệ ở đây
        int roomId = 99;
        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName("CCCC");
        o.setQuantity(3);
        o.setPrice(new BigDecimal(99119));
        o.setCategoryId(1);

        Assertions.assertFalse(s.updateRoom(o, roomId));
    }

    @Test
    @DisplayName("...")
    @Tag("upp-room")
    public void testUpdateRoomWithInValidName() {
        //          Thay giá trị id bất kì hợp lệ ở đây
        int roomId = 25;
        RoomService s = new RoomService(conn);

        Room o = new Room();
//        Tên phòng dài quá chiều dài cho phép: lengh = 10
        o.setName("CCCCasdasdsadas@432");
        o.setQuantity(3);
        o.setPrice(new BigDecimal(99119));
        o.setCategoryId(1);

        Assertions.assertFalse(s.updateRoom(o, roomId));
    }

    @Test
    @DisplayName("...")
    @Tag("upp-room")
    public void testUpdateRoomWithInValidQuantity() {
        //          Thay giá trị id bất kì hợp lệ ở đây
        int roomId = 25;

        int InValidQuantity = -1;

        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName("CCCC");
        o.setQuantity(InValidQuantity);
        o.setPrice(new BigDecimal(99119));
        o.setCategoryId(1);

        Assertions.assertFalse(s.updateRoom(o, roomId));
    }

    @Test
    @DisplayName("...")
    @Tag("upp-room")
    public void testUpdateRoomWithInValidPrice() {
        //          Thay giá trị id bất kì hợp lệ ở đây
        int roomId = 25;

        int InValidPrice = 50000;

        RoomService s = new RoomService(conn);

        Room o = new Room();
        o.setName("CCCC");
        o.setQuantity(3);
        o.setPrice(new BigDecimal(InValidPrice));
        o.setCategoryId(1);

        Assertions.assertFalse(s.updateRoom(o, roomId));
    }

    @Test
    @DisplayName("...")
    @Tag("delete-room")
    public void testDeleteRoomWithInValidId() {
        int roomId = 99;

        RoomService s = new RoomService(conn);

        Assertions.assertFalse(s.deleteProduct(roomId));
    }

    @Test
    @DisplayName("...")
    @Tag("delete-room")
    public void testDeleteRoomWithValidId() {
        int roomId = 24;

        RoomService s = new RoomService(conn);

        Assertions.assertTrue(s.deleteProduct(roomId));
    }

}
