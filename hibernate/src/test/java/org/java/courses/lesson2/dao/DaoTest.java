package org.java.courses.lesson2.dao;

import org.hibernate.LazyInitializationException;
import org.java.courses.lesson2.ManagerFactory;
import org.java.courses.lesson2.model.ContactInfo;
import org.java.courses.lesson2.model.Discipline;
import org.java.courses.lesson2.model.Group;
import org.java.courses.lesson2.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DaoTest {
    static StudentDao studentDao;
    static GroupDao groupDao;
    static DisciplineDao disciplineDao;

    static EntityManagerFactory emFactory;

    @BeforeAll
    public static void beforeAll() {
        emFactory = ManagerFactory.create("org.java.courses_h2");

        studentDao = new StudentDao(emFactory);
        groupDao = new GroupDao(emFactory);
        disciplineDao = new DisciplineDao(emFactory);
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

        Group savedGroup = groupDao.findById(group.getId());

        assertNotNull(savedGroup.getId());
        //Чтобы не было ошибки, нам необходимо иметь сессию. Есть несколько подходов. В рамках одной http сессии держать одну сессию к бд.
        //https://stackoverflow.com/questions/4225638/how-frequently-should-i-create-an-entitymanager
        assertThrows(LazyInitializationException.class, () -> savedGroup.getStudents().size());

        //можно открыть сессию и перевести сущность из detached в persistence состояние.
        EntityManager entityManager = emFactory.createEntityManager();
        Group reattachedGroup = entityManager.merge(savedGroup);
        assertTrue(reattachedGroup.getStudents().size() > 0);
        entityManager.close();
    }

    @Test
    public void testQueries() {
        Discipline discipline1 = Discipline.builder().code(1).description("description").build();
        Discipline discipline2 = Discipline.builder().code(2).description("description2").build();

        disciplineDao.save(discipline1);
        disciplineDao.save(discipline2);

        Group group = Group.builder().name("group2").direction("direction2").build();
        groupDao.save(group);

        ContactInfo contactInfo = ContactInfo.builder().email("email").telephoneNumber("number").build();
        Student student = Student.builder().name("name").contact(contactInfo).group(group).disciplines(Set.of(discipline1, discipline2)).build();
        studentDao.save(student);

        ContactInfo contactInfo2 = ContactInfo.builder().email("email2").telephoneNumber("number2").build();
        Student student2 = Student.builder().name("name2").contact(contactInfo2).disciplines(Set.of(discipline1)).build();
        studentDao.save(student2);

//        List<Student> students = studentDao.findWithContact();
//        assertTrue(students.size() > 0);
//        assertEquals(2, students.size());

//        List<Student> students2 = studentDao.findWithContactRaw();
//        assertTrue(students2.size() > 0);
//        assertEquals(2, students2.size());
//
//        List<Student> findAllStudent = studentDao.findAll();
//        assertTrue(findAllStudent.size() > 0);
//        assertEquals(2, findAllStudent.size());
//
//        List<Student> allByGroup = studentDao.findAllByGroup(group);
//        assertTrue(allByGroup.size() > 0);
//        assertEquals(1, allByGroup.size());
//
//        List<Student> another1 = studentDao.findAllNamedQuery();
//        List<Student> another2 = studentDao.findAllWithContactNamedQuery();
//        List<Student> another2 = studentDao.findAllWithDisciplinesNamedQuery();
//        List<Student> another3 = studentDao.findByIdNamedQuery(student.getId());
//
//        List<Student> result5 = studentDao.findByCriteriaQuery("name2", "email2");
//        System.out.println("hello");
//
        List<Student> result6 = studentDao.findByCriteriaQuery("name2", "email2", Set.of(1,2,3,4));
        System.out.println("hello");
    }

    @Test
    public void testFindAll() {
        Group group = Group.builder().name("group2").direction("direction2").build();
        groupDao.save(group);

        ContactInfo contactInfo = ContactInfo.builder().email("email").telephoneNumber("number").build();
        Student student = Student.builder().name("name").contact(contactInfo).group(group).build();
        studentDao.save(student);

        ContactInfo contactInfo2 = ContactInfo.builder().email("email2").telephoneNumber("number2").build();
        Student student2 = Student.builder().name("name2").contact(contactInfo2).group(group).build();
        studentDao.save(student2);

        List<Student> students = studentDao.findAll();
        assertTrue(students.size() > 0);
        assertEquals(2, students.size());

    }
}