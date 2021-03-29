/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_room;
    private String index;
    private int quantity;
    private BigDecimal price;
    private String image;

//    EAGER khi truy cập room thì lấy luôn thông tin của category tương ứng
//    LAZY thì ngược lại - EAGER là default
//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

//    @ManyToMany
//    @JoinTable(
//            name="ten thuoc tinh nao do",
//            joinColumns = {
//                @JoinColumn(name = "id")
//            },
//            inverseJoinColumns = {
//                @JoinColumn(name = "id")
//            }
//    )
//    private Set<ManyToMany> manytomany;

    /**
     * @return the id_room
     */
    public int getId_room() {
        return id_room;
    }

    /**
     * @param id_room the id_room to set
     */
    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
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
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }



}
