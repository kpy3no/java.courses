package org.java.courses.lesson2.dao;

import org.java.courses.lesson2.ManagerFactory;
import org.java.courses.lesson2.model.ContactInfo;
import org.java.courses.lesson2.model.Discipline;
import org.java.courses.lesson2.model.Group;
import org.java.courses.lesson2.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SharedDaoTest {
    static SharedDao<Student> studentDao;
    static SharedDao<Group> groupDao;
    static SharedDao<Discipline> disciplineDao;

    @BeforeAll
    public static void beforeAll() {
        EntityManagerFactory emFactory = ManagerFactory.create("org.java.courses_h2");

        studentDao = new SharedDao<>(Student.class, emFactory.createEntityManager());
        groupDao = new SharedDao<>(Group.class, emFactory.createEntityManager());
        disciplineDao = new SharedDao<>(Discipline.class, emFactory.createEntityManager());
    }

    @Test
    public void testOneToOne() {
        ContactInfo contactInfo = ContactInfo.builder().email("email").telephoneNumber("number").build();
        Student student = Student.builder().name("name").contact(contactInfo).build();

        student = studentDao.save(student);

        Student savedStudent = studentDao.findById(student.getId());

        assertNotNull(savedStudent.getId());
        assertEquals("name", savedStudent.getName());
        assertNotNull(savedStudent.getContact().getId());
        assertEquals("email", savedStudent.getContact().getEmail());
    }

    @Test
    public void testManyToOne() {
        Group group = Group.builder().name("group2").direction("direction2").build();
        groupDao.save(group);

        ContactInfo contactInfo = ContactInfo.builder().email("email").telephoneNumber("number").build();
        Student student = Student.builder().name("name").contact(contactInfo).group(group).build();
        student = studentDao.save(student);

        Student savedStudent = studentDao.findById(student.getId());

        assertNotNull(savedStudent.getId());
        assertNotNull(savedStudent.getGroup().getId());
        assertEquals("group2", savedStudent.getGroup().getName());
    }

    @Test
    public void testOneToMany() {
        Group group = Group.builder().name("group1").direction("direction1").build();
        groupDao.save(group);

        ContactInfo contactInfo = ContactInfo.builder().email("email").telephoneNumber("number").build();
        Student student = Student.builder().name("name").contact(contactInfo).group(group).build();
        studentDao.save(student);

        ContactInfo contactInfo2 = ContactInfo.builder().email("email2").telephoneNumber("number2").build();
        Student student2 = Student.builder().name("name2").contact(contactInfo2).group(group).build();
        studentDao.save(student2);

        groupDao.clear();

        Group savedGroup = groupDao.findById(group.getId());

        assertNotNull(savedGroup.getId());
        assertTrue(savedGroup.getStudents().size() > 0);
    }

    @Test
    public void testManyToMany() {
        Discipline discipline1 = Discipline.builder().code(1).description("description").build();
        Discipline discipline2 = Discipline.builder().code(2).description("description2").build();

        disciplineDao.save(discipline1);
        disciplineDao.save(discipline2);

        ContactInfo contactInfo = ContactInfo.builder().email("email").telephoneNumber("number").build();
        Student student = Student.builder().name("name").contact(contactInfo).disciplines(Set.of(discipline1, discipline2)).build();
        studentDao.save(student);

        ContactInfo contactInfo2 = ContactInfo.builder().email("email2").telephoneNumber("number2").build();
        Student student2 = Student.builder().name("name2").contact(contactInfo2).disciplines(Set.of(discipline1)).build();
        studentDao.save(student2);

        studentDao.clear();
        Student savedStudent = studentDao.findById(student.getId());
        assertTrue(savedStudent.getDisciplines().size() > 0);
    }
}