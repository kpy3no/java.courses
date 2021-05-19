package org.java.courses.lesson2.dao;

import org.java.courses.lesson2.model.Group;

import javax.persistence.EntityManagerFactory;

public class GroupDao extends Dao<Group> {
    public GroupDao(EntityManagerFactory emFactory) {
        super(emFactory, Group.class);
    }
}
