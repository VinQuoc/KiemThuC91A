/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.pojo.SystemRule;
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
public class SystemRuleService {
    private Connection conn;

    public SystemRuleService(Connection conn) {
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
    
    public List<SystemRule> getSystemRules() throws SQLException {
        String sql = "SELECT * FROM rule";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<SystemRule> rules = new ArrayList<>();
        while (rs.next()) {
            SystemRule u = new SystemRule();
            u.setId(rs.getInt("id"));
            u.setRule(rs.getString("rule"));
            u.setDescription(rs.getString("description"));
            u.setStatus(rs.getBoolean("status"));
            u.setAdmin_id(rs.getInt("admin_id"));
            rules.add(u);
        }
        return rules;
    }
    
        public List<SystemRule> getRules() throws SQLException {
            
        String sql = "SELECT  * FROM rule";
        PreparedStatement stm = this.getConn().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<SystemRule> rules = new ArrayList<>();
        while (rs.next()) {
            SystemRule r = new SystemRule();
            r.setId(rs.getInt("id"));
            r.setRule(rs.getString("rule"));
            r.setDescription(rs.getString("description"));
            r.setStatus(rs.getBoolean("status"));
            rules.add(r);
        }
        return rules;
    }

    public boolean addRule(SystemRule r) {
        if (r.getRule().length() < 1) {
            return false;
        }
        try {
            String sql = "INSERT INTO rule(rule, description, status) VALUES(?, ?, ?)";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, r.getRule());
            stm.setString(2, r.getDescription());
            stm.setBoolean(3, r.isStatus());
            
            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateRule(SystemRule r, int ruleId) { 
        if (r.getRule().length() < 1) {
            return false;
        }
        if (ruleId == 0) {
            return false;
        }
        try {
            String sql = "UPDATE rule\n"
                    + "SET description=?, status=?\n"
                    + "WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, r.getDescription());
            stm.setBoolean(2, r.isStatus());
            stm.setInt(3, ruleId);

            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteRule(int ruleid) {
        try {
            String sql = "DELETE FROM rule WHERE id=?";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setInt(1, ruleid);

            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(RoomService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }   
}
