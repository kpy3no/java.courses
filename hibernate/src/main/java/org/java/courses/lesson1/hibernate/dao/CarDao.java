package org.java.courses.lesson1.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.java.courses.lesson1.hibernate.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CarDao {
    public final static Logger logger = LoggerFactory.getLogger(CarDao.class);

    private final SessionFactory sessionFactory;

    public static CarDao create() {
        return create("hibernate.annotation.cfg.xml");
    }

    public static CarDao create(String configuration) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(configuration).build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        return new CarDao(meta.getSessionFactoryBuilder().build());
    }
    
    public CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Method 1: This Method Used To Create A New Car Record In The Database Table
    public Car createRecord(Car car) {
        Session session = sessionFactory.openSession();

        //Creating Transaction Object
        Transaction transaction = session.beginTransaction();
        session.save(car);

        // Transaction Is Committed To Database
        transaction.commit();

        // Closing The Session Object
        session.close();
        logger.info("Successfully Created " + car.toString());
        return car;
    }

    // Method 2: This Method Is Used To Display The Records From The Database Table
    public List<Car> findAll() {
        Session session = sessionFactory.openSession();
        List<Car> cars = session.createQuery("FROM Car", Car.class).list();

        // Closing The Session Object
        session.close();
        logger.info("Car Records Available In Database Are?= " + cars.size());
        return cars;
    }

    // Method 3: This Method Is Used To Update A Record In The Database Table
    public Car updateRecord(Car car) {
        Session session = sessionFactory.openSession();

        //Creating Transaction Object
        Transaction transaction = session.beginTransaction();
        Car savedCar = session.load(Car.class, car.getId());
        savedCar.setModel(car.getModel());

        // Transaction Is Committed To Database
        transaction.commit();

        // Closing The Session Object
        session.close();
        logger.info("Car Record Is Successfully Updated!= " + car.toString());

        return savedCar;
    }

    // Method 4(a): This Method Is Used To Delete A Particular Record From The Database Table
    public void deleteRecord(Integer id) {
        Session session = sessionFactory.openSession();

        //Creating Transaction Object
        Transaction transObj = session.beginTransaction();
        Car car = session.load(Car.class, id);
        session.delete(car);

        // Transaction Is Committed To Database
        transObj.commit();

        // Closing The Session Object
        session.close();
        logger.info("Successfully Record Is Successfully Deleted!=  " + car.toString());

    }

    // Method 4(b): This Method To Find Particular Record In The Database Table
    public Car findRecordById(Integer id) {
        Session session = sessionFactory.openSession();
        Car car = session.get(Car.class, id);
        // Closing The Session Object
        session.close();

        return car;
    }
}
