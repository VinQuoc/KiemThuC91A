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
public class Order {
    private int id;
    private BigDecimal total_price;
    private boolean pay_status;
    private int employee_id;

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
     * @return the employee_id
     */
    public int getEmployee_id() {
        return employee_id;
    }

    /**
     * @param employee_id the employee_id to set
     */
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
