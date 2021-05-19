package org.java.courses.lesson2;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManagerFactory {
    public static EntityManagerFactory create(String persistenceUnitName) {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }
}
