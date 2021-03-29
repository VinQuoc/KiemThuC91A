/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lqv.hotelmanager;

import com.lqv.pojo.Category;
import com.lqv.pojo.Room;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author DELL
 */
public class HibernateUtil {

    private final static SessionFactory FACTORY;

    static {
        Configuration conf = new Configuration();

        Properties props = new Properties();
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.URL, "jdbc:mysql://localhost:3306/hoteldb");
        props.put(Environment.USER, "root");
        props.put(Environment.PASS, "Abc1234%^&");
        
        conf.setProperties(props);
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Room.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();

        FACTORY = conf.buildSessionFactory(registry);
    }

    public static SessionFactory getFactory() {
        return FACTORY;
    }
}
