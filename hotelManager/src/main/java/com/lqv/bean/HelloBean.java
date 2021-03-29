/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.bean;

import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author DELL
 */
@ManagedBean
@Named(value = "helloBean")
@Dependent
public class HelloBean {
    
    public String getMessage() {
        return "Welcome to our Websites";
    }
}
