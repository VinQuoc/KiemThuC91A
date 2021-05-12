/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class EmployeeService {

    private Connection conn;

    public EmployeeService(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Employee getEmployeeById(int employeeId) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM employee WHERE id=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, employeeId);

        ResultSet rs = stm.executeQuery();

        Employee e = null;
        while (rs.next()) {
            e = new Employee();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setRole(rs.getString("role"));
            break;
        }

        return e;
    }

    public boolean checkAcc(String user, String pass) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM employee WHERE username = ? AND password = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, user);
        stm.setString(2, pass);

        ResultSet rs = stm.executeQuery();
//        System.out.println(rs);

        String username = "";
        String password = "";
        while (rs.next()) {
//            System.out.println(rs.getString("username"));
//            System.out.println(rs.getString("password"));
            username = rs.getString("username");
            password = rs.getString("password");
        }

//        System.out.println(username);
//        System.out.println(password);
        if (username.equals(user) && password.equals(pass)) {
            return true;
        }
        return false;
    }

    public void getEmp(String user, String pass, Employee emp) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        String sql = "SELECT * FROM employee WHERE username = ? AND password = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, user);
        stm.setString(2, pass);

        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setRole(rs.getString("role"));
        }
        
    }

}
