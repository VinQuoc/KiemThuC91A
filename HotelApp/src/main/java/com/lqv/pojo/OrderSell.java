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
public class OrderSell {
    private int id;
    private BigDecimal total_price;
    private boolean pay_status;
    private int employeeId;

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
     * @return the total_price
     */
    public BigDecimal getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    /**
     * @return the pay_status
     */
    public boolean isPay_status() {
        return pay_status;
    }

    /**
     * @param pay_status the pay_status to set
     */
    public void setPay_status(boolean pay_status) {
        this.pay_status = pay_status;
    }

    /**
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
