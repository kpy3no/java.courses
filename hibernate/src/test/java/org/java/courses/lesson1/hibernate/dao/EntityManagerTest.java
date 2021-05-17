package org.java.courses.lesson1.hibernate.dao;

import org.java.courses.lesson1.hibernate.model.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerTest {
    static EntityManager em;

    @BeforeAll
    public static void beforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.java.courses_entitymanager");
        em = emf.createEntityManager();
    }

    @Test
    public void emCrudTest() {
        //Persisting Entities
        em.getTransaction().begin();

        Car car = new Car();
        car.setModel("testModel");

        em.persist(car);
        em.getTransaction().commit();

        //Loading Entities
        car = em.find(Car.class, car.getId());
        assertEquals("testModel", car.getModel());
    }
}
