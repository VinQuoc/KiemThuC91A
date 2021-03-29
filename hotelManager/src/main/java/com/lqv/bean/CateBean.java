/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.bean;

import com.lqv.pojo.Category;
import com.lqv.pojo.Room;
import com.lqv.service.CategoryService;
import com.lqv.service.RoomService;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author DELL
 */ 
@ManagedBean
@Named(value = "cateBean")
@SessionScoped
public class CateBean implements Serializable{

    private final static CategoryService cateService = new CategoryService();
    /**
     * Creates a new instance of CateBean
     */
    public CateBean() {
    }
    
    public List<Category> getCategories() {
        return cateService.getCategories();
    }
}
