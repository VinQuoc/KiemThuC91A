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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
