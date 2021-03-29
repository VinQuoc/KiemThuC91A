/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.bean;

import com.lqv.pojo.Category;
import com.lqv.pojo.Room;
import com.lqv.service.RoomService;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author DELL
 */
@ManagedBean
@Named(value = "roomBean")
@RequestScoped
public class RoomBean {
    private String index;
    private BigDecimal price;
    private int quantity;
    private String image;
    private Category category;

    private final static RoomService roomService = new RoomService();

    /**
     * Creates a new instance of RoomBean
     */
    public RoomBean() {
    }

    public List<Room> getRooms() {
        return roomService.getRooms(null);
    }
    
//    public String addRoom() {
//        Room r = new Room();
//        r.setIndex(this.index);
//        r.setPrice(this.price);
//        r.setCategory(this.category);
//        r.setQuantity(this.quantity);
//        r.setImage(this.image);
//        
//        if (roomService.addRoom(r) == true)
//            return "room-list?faces-redirect=true";
//        
//        return "room";
//    }
}
