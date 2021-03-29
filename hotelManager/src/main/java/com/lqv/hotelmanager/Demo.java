/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelmanager;

import com.lqv.pojo.Category;
import com.lqv.pojo.Room;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author DELL
 */
public class Demo {

    private static final SessionFactory factory = HibernateUtil.getFactory();

    public static void queryDemo() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Room> query = builder.createQuery(Room.class);
            Root<Room> root = query.from(Room.class);
            query.select(root);

//            Lọc dữ liệu theo tên
            Predicate r1 = builder.like(root.get("name").as(String.class), "%Tencantim%");
            Predicate r2 = builder.like(root.get("price").as(String.class), "%price%");
            Predicate r3 = builder.between(root.get("price").as(BigDecimal.class),
                    new BigDecimal(25000), new BigDecimal(35000));
            query.where(builder.or(r1, r2));
            query.where(builder.and(builder.or(r1, r2), r3));

            Query<Room> r = session.createQuery(query);
            List<Room> rooms = r.getResultList();

//            rooms.forEach(r -> System.out.printf("%s: %.2f\n",
//                    r.getName(), r.getPrice()));
        }
    }

    public static void statsDemo() {
        try ( Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            
            Root<Object[]> root = query.from(Object[].class);
            
            query.multiselect(builder.count(root.get("id")),
                    builder.max(root.get("price").as(BigDecimal.class)),
                    builder.avg(root.get("price").as(BigDecimal.class)));

            Query<Object[]> r = session.createQuery(query);
            Object[] re = r.getSingleResult();
            System.out.println("So luong phong: " + re[0]);
            System.out.println("Phong gia cao nhat: " + re[1]);
            System.out.println("gia tb: " + re[2]);
        }
    }
    
    
    public static void groupDemo() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            
            Root<Room> rRoot = query.from(Room.class);
            Root<Category> cRoot = query.from(Category.class);
//            thiết lập quan hệ join 2 bảng
            query = query.where(builder.equal(rRoot.get("category"), cRoot.get("id_category")));
            
            query.multiselect(cRoot.get("name").as(String.class),
                    builder.count(rRoot.get("id_room")),
                    builder.max(rRoot.get("price").as(BigDecimal.class)));
            query.groupBy(cRoot.get("name").as(String.class));
            query.orderBy(builder.asc(cRoot.get("name").as(String.class)));
            
            Query<Object[]> q = session.createQuery(query);
            List<Object[]> k = q.getResultList();
//            k.forEach(obj -> {
//                System.out.printf("%s: %d - %.2f", obj[0], obj[1], obj[2]);
//            });
        }
    }
    
    

    

    public static void main(String[] args) {
        groupDemo();

//        try ( Session session = HibernateUtil.getFactory().openSession()) {
////            Query<Category> query = session.createQuery("FROM Category");
////            List<Category> cats = query.list();
////            cats.forEach(c -> System.out.println(c.getName()));
//
//            session.getTransaction().begin();
//
//            Category c = session.get(Category.class, 2);
//
//            Room r = new Room();
//            r.setIndex("V999"); 
//            r.setImage("ssfsd");
//            r.setPrice(new BigDecimal(555555));
//            r.setQuantity(3);
//            r.setCategory(c);
//
//            session.save(r);
//            session.getTransaction().commit();
//
////            ManyToMany m1 = session.get(ManyToMany, 1);
////            ManyToMany m2 = session.get(ManyToMany, 2);
////            Set<ManyToMany> m = new HashSet<>();
////            m.add(m1);
////            m.add(m2);
////            r.setManyToMany(m);
//        }
    }
}
