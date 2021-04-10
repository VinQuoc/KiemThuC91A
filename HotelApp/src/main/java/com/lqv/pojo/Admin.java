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
public class Admin {
    private int id;
    private String user_adm;
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
     * @return the user_adm
     */
    public String getUser_adm() {
        return user_adm;
    }

    /**
     * @param user_adm the user_adm to set
     */
    public void setUser_adm(String user_adm) {
        this.user_adm = user_adm;
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
