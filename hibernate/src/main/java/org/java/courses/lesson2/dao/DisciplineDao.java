package org.java.courses.lesson2.dao;

import org.java.courses.lesson2.model.Discipline;

import javax.persistence.EntityManagerFactory;

public class DisciplineDao extends Dao<Discipline> {
    public DisciplineDao(EntityManagerFactory emFactory) {
        super(emFactory, Discipline.class);
    }
}
