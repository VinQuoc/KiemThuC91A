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
public class OrderOwner {
    private int id;
    private String name;
    private String phone;
    private String IS_Number;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the IS_Number
     */
    public String getIS_Number() {
        return IS_Number;
    }

    /**
     * @param IS_Number the IS_Number to set
     */
    public void setIS_Number(String IS_Number) {
        this.IS_Number = IS_Number;
    }
}
