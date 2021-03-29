/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.service;

import com.lqv.hotelmanager.HibernateUtil;
import com.lqv.pojo.Room;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author DELL
 */
public class RoomService {
    private final static SessionFactory factory = HibernateUtil.getFactory();
    
//    public List<Room> getRooms() {
//        try (Session session = factory.openSession()) {
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Room> query = builder.createQuery(Room.class);
//            Root<Room> root = query.from(Room.class);
//            query.select(root);
//            
//            return session.createQuery(query).getResultList();
//        }
//    }
    
    public List<Room> getRooms(String kw) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Room> query = builder.createQuery(Room.class);
            Root<Room> root = query.from(Room.class);
            query.select(root);
            
            if (kw != null && !kw.isEmpty()) {
                String r = String.format("%%%s%%", kw);
                Predicate r1 = builder.like(root.get("name").as(String.class), r);
                Predicate r2 = builder.like(root.get("index").as(String.class), r);
                
                query = query.where(builder.or(r1,r2));
            }
            
            return session.createQuery(query).getResultList(); 
        }
    }
    
//    public boolean addRoom(Room r) {
//        try (Session session = factory.openSession()) {
//            try {
//                session.getTransaction().begin();
//                session.saveOrUpdate(r);
//                session.getTransaction().commit();
//            } catch (Exception ex) {
//                session.getTransaction().rollback();
//                return false;
//            }
//        }
//        return true;
//    }
}
