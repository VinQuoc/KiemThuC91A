/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.pojo;

/**
 *
 * @author DELL
 */
public class Account {
    private int id;
    private String user_emp;
    private String password;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the user_emp
     */
    public String getUser_emp() {
        return user_emp;
    }

    /**
     * @param user_emp the user_emp to set
     */
    public void setUser_emp(String user_emp) {
        this.user_emp = user_emp;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
