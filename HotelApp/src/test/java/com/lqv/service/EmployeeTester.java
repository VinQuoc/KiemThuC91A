/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.Employee;
import com.lqv.pojo.Room;
import java.sql.Connection;
import java.sql.SQLException;
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
public class EmployeeTester {

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
    @DisplayName("...")
    @Tag("getEmployeeById")
    public void testGetEmployeeByIdWithInvalidId() {
        boolean check;
        int empId = 99;
        try {
            EmployeeService s = new EmployeeService(conn);

            if (s.getEmployeeById(empId) != null) {
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
    @Tag("getEmployeeById")
    public void testGetEmployeeByIdWithValidId() {
        boolean check;
        int empId = 1;
        try {
            EmployeeService s = new EmployeeService(conn);

            if (s.getEmployeeById(empId) != null) {
                check = true;
            } else {
                check = false;
            }

            Assertions.assertTrue(check);
        } catch (SQLException ex) {
            Logger.getLogger(RoomTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Tag("checkAcc")
    @ParameterizedTest
    @CsvSource({"nhanvien1, qwe, true", "nhanvien2, zxcd, false", "quanly, 123, true"})
    public void testCheckAcc(String username, String password, boolean expected) {
        try {
            EmployeeService s = new EmployeeService(conn);
            Assertions.assertEquals(expected, s.checkAcc(username, password));
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Tag("getEmp")
    public void testGetEmpWithValidValue() {
        try {
            String username = "quanly";
            String password = "123";
            boolean check;
            Employee emp = new Employee();
            EmployeeService s = new EmployeeService(conn);
            s.getEmp(username, password, emp);
            if (emp != null) {
                check = true;
            } else {
                check = false;
            }
            Assertions.assertTrue(check);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Tag("getEmp")
    public void testGetEmpWithInvalidValue() {
        try {
            String username = "quanly1";
            String password = "123";
            boolean check;
            Employee emp = new Employee();
            EmployeeService s = new EmployeeService(conn);
            s.getEmp(username, password, emp);
            if (emp == null) {
                check = true;
            } else {
                check = false;
            }
            Assertions.assertFalse(check);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Tag("checkAcc")
    @ParameterizedTest
    @CsvSource({"nhanvien1, qwe, true", "nhanvien23, zxcd, false", "quanly, 123, true"})
    public void testGetEmp(String username, String password, boolean expected) {
        try {
            Employee emp = new Employee();
            EmployeeService s = new EmployeeService(conn);
            s.getEmp(username, password, emp);
            boolean check;
            if (emp.getId() > 0) {
                check = true;
            } else {
                check = false;
            }
            Assertions.assertEquals(expected, check);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
