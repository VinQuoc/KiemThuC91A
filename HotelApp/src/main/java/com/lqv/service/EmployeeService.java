/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.hotelapp.App;
import com.lqv.pojo.Employee;
import com.lqv.pojo.Room;
import java.math.BigDecimal;
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

        String username = "";
        String password = "";
        while (rs.next()) {
            username = rs.getString("username");
            password = rs.getString("password");
        }
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

    public boolean addEmp(Employee e) {
        if (e.getName().length() < 1) {
            return false;
        }
        if (e.getPhone().length() < 1) {
            return false;
        }
        try {
            String sql = "INSERT INTO employee(name, phone, email) VALUES(?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, e.getName());
            stm.setString(2, e.getPhone());
            stm.setString(3, e.getEmail());

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean updateEmp(Employee e, int empId) {
        if (e.getName().length() < 1) {
            return false;
        }
        if (e.getPhone().length() < 1) {
            return false;
        }
        try {
            String sql = "UPDATE employee\n"
                    + "SET name=?, phone=?, email=?\n"
                    + "WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, e.getName());
            stm.setString(2, e.getPhone());
            stm.setString(3, e.getEmail());
            stm.setInt(4, empId);

            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean updateEmpAcc(Employee e, int empId) {
        if (e.getUsername().length() < 1) {
            return false;
        }
        if (e.getPassword().length() < 1) {
            return false;
        }
        try {
            String sql = "UPDATE employee\n"
                    + "SET username=?, password=?\n"
                    + "WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, e.getUsername());
            stm.setString(2, e.getPassword());
            stm.setInt(3, empId);

            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteEmp(int empId) {
        try {
            String sql = "DELETE FROM employee WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, empId);

            int rows = stm.executeUpdate();

            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public List<Employee> getEmployees(String kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException("error");
        }

        String sql = "SELECT  *\n"
                + "FROM employee\n"
                + " WHERE employee.name like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        stm.setString(1, kw);

        ResultSet rs = stm.executeQuery();
        List<Employee> emps = new ArrayList<>();
        while (rs.next()) {
            Employee e = new Employee();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setPhone(rs.getString("phone"));
            e.setEmail(rs.getString("email"));
            e.setRole(rs.getString("role"));
            e.setUsername(rs.getString("username"));

            emps.add(e);
        }

        return emps;
    }

}
