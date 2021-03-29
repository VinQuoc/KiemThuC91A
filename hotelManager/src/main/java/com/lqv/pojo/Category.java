/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.pojo;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_category;
    private String name;

//  default là LAZY
//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "category")
    private Set<Room> room;

    /**
     * @return the id_category
     */
    public int getId_category() {
        return id_category;
    }

    /**
     * @param id_category the id_category to set
     */
    public void setId_category(int id_category) {
        this.id_category = id_category;
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
     * @return the room
     */
    public Set<Room> getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Set<Room> room) {
        this.room = room;
    }


 
}
