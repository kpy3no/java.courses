package org.java.courses.lesson1.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.java.courses.lesson1.hibernate.dao.CarDao;
import org.java.courses.lesson1.hibernate.model.Car;
import org.java.courses.lesson1.hibernate.model.City;
import org.java.courses.lesson1.hibernate.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        createXMLContext();
        createAnnotationContext();
        useCarDao();
    }

    public static void createXMLContext() {
        SessionFactory factory;
        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
//
//        Session session = factory.openSession();
//        List<User> users = session.createQuery("FROM User", User.class).list();
//
//        // Closing The Session Object
//        session.close();
//        System.out.println(users);
        createRecord(new City("Novosibirsk"), factory);
        createRecord(new User("Vasya"), factory);
    }

    public static void createAnnotationContext() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.annotation.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory sessionFactory = meta.getSessionFactoryBuilder().build();

//        Session session = sessionFactory.openSession();
//        List<Car> cars = session.createQuery("FROM Car", Car.class).list();
//
//        // Closing The Session Object
//        session.close();
//        System.out.println(cars);
    }

    // Method 1: This Method Used To Create A New Car Record In The Database Table
    public static <T> T createRecord(T record, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        //Creating Transaction Object
        Transaction transaction = session.beginTransaction();
        session.save(record);

        // Transaction Is Committed To Database
        transaction.commit();

        // Closing The Session Object
        session.close();
        return record;
    }

    public static void useCarDao() {
        //CREATE
        CarDao carDao = CarDao.create();
        Car car = carDao.createRecord(new Car("нива"));

        carDao.acceptToAll(car1 -> {
            if (car1.getModel().equals("нива")) {
                car1.setModel("жигули");
            }
        });

        car = carDao.findRecordById(car.getId());
        System.out.println(car.getModel());
    }
}
