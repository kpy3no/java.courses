package org.java.courses.lesson1.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbOperations {

    public final static Logger logger = LoggerFactory.getLogger(DbOperations.class);

    // Method Used To Create The Hibernate's SessionFactory Object
    public static SessionFactory getSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, Service Registry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate Session Factory Instance
        SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return factoryObj;
    }

    // Method 1: This Method Used To Create A New Car Record In The Database Table
    public static Integer createRecord(Car car) {
        Session sessionObj = getSessionFactory().openSession();

        //Creating Transaction Object
        Transaction transObj = sessionObj.beginTransaction();
        sessionObj.save(car);

        // Transaction Is Committed To Database
        transObj.commit();

        // Closing The Session Object
        sessionObj.close();
        logger.info("Successfully Created " + car.toString());
        return car.getId();
    }

    // Method 2: This Method Is Used To Display The Records From The Database Table
    @SuppressWarnings("unchecked")
    public static List displayRecords() {
        Session sessionObj = getSessionFactory().openSession();
        List CarsList = sessionObj.createQuery("FROM Car").list();

        // Closing The Session Object
        sessionObj.close();
        logger.info("Car Records Available In Database Are?= " + CarsList.size());
        return CarsList;
    }

    // Method 3: This Method Is Used To Update A Record In The Database Table
    public static void updateRecord(Car car) {
        Session sessionObj = getSessionFactory().openSession();

        //Creating Transaction Object
        Transaction transObj = sessionObj.beginTransaction();
        Car stuObj = (Car) sessionObj.load(Car.class, car.getId());
        stuObj.setModel(car.getModel());

        // Transaction Is Committed To Database
        transObj.commit();

        // Closing The Session Object
        sessionObj.close();
        logger.info("Car Record Is Successfully Updated!= " + car.toString());
    }

    // Method 4(a): This Method Is Used To Delete A Particular Record From The Database Table
    public static void deleteRecord(Integer CarId) {
        Session sessionObj = getSessionFactory().openSession();

        //Creating Transaction Object
        Transaction transObj = sessionObj.beginTransaction();
        Car studObj = findRecordById(CarId);
        sessionObj.delete(studObj);

        // Transaction Is Committed To Database
        transObj.commit();

        // Closing The Session Object
        sessionObj.close();
        logger.info("Successfully Record Is Successfully Deleted!=  " + studObj.toString());

    }

    // Method 4(b): This Method To Find Particular Record In The Database Table
    public static Car findRecordById(Integer CarId) {
        Session sessionObj = getSessionFactory().openSession();
        Car stu = (Car) sessionObj.load(Car.class, CarId);

        // Closing The Session Object
        sessionObj.close();
        return stu;
    }

    // Method 5: This Method Is Used To Delete All Records From The Database Table
    public static void deleteAllRecords() {
        Session sessionObj = getSessionFactory().openSession();

        //Creating Transaction Object
        Transaction transObj = sessionObj.beginTransaction();
        Query queryObj = sessionObj.createQuery("DELETE FROM Car");
        queryObj.executeUpdate();

        // Transaction Is Committed To Database
        transObj.commit();

        // Closing The Session Object
        sessionObj.close();
        logger.info("Successfully Deleted All Records From The Database Table!");
    }
}
