/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.pojo;

import java.math.BigDecimal;

/**
 *
 * @author DELL
 */
public class ListOrder {
    private String nameCus;
    private String phoneCus;
    private String ISNumberCus;
    private String byEmp;
    private BigDecimal price;
    private String timeStart;
    private String timeEnd;
    private String nameRoom;
    private int quantity;
    private String typeRoom;

    /**
     * @return the nameCus
     */
    public String getNameCus() {
        return nameCus;
    }

    /**
     * @param nameCus the nameCus to set
     */
    public void setNameCus(String nameCus) {
        this.nameCus = nameCus;
    }

    /**
     * @return the phoneCus
     */
    public String getPhoneCus() {
        return phoneCus;
    }

    /**
     * @param phoneCus the phoneCus to set
     */
    public void setPhoneCus(String phoneCus) {
        this.phoneCus = phoneCus;
    }

    /**
     * @return the ISNumberCus
     */
    public String getISNumberCus() {
        return ISNumberCus;
    }

    /**
     * @param ISNumberCus the ISNumberCus to set
     */
    public void setISNumberCus(String ISNumberCus) {
        this.ISNumberCus = ISNumberCus;
    }

    /**
     * @return the byEmp
     */
    public String getByEmp() {
        return byEmp;
    }

    /**
     * @param byEmp the byEmp to set
     */
    public void setByEmp(String byEmp) {
        this.byEmp = byEmp;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the timeStart
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * @param timeStart the timeStart to set
     */
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @return the nameRoom
     */
    public String getNameRoom() {
        return nameRoom;
    }

    /**
     * @param nameRoom the nameRoom to set
     */
    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the timeEnd
     */
    public String getTimeEnd() {
        return timeEnd;
    }

    /**
     * @param timeEnd the timeEnd to set
     */
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    /**
     * @return the typeRoom
     */
    public String getTypeRoom() {
        return typeRoom;
    }

    /**
     * @param typeRoom the typeRoom to set
     */
    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }
    

}
