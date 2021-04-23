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
    public Employee getEmployeeById(int employeeId) throws SQLException {
       Connection conn = JdbcUtils.getConn();
       String sql ="SELECT * FROM employee WHERE id=?";
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
}
