package org.java.courses.lesson2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.java.courses.lesson2.model.ContactInfo;
import org.java.courses.lesson2.model.Discipline;
import org.java.courses.lesson2.model.Group;
import org.java.courses.lesson2.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = initFactory();
        Session session = factory.openSession();
        List<Student> students = session.createQuery("FROM Student", Student.class).list();

        session.close();

        for (Student student : students) {
            System.out.println(String.format("Student %s has group %s", student.getName(), student.getGroup()));
        }
    }

    public static SessionFactory initFactory() {
        try {
            return new Configuration().configure("lesson2/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void initData(SessionFactory sessionFactory) {
        Group group = Group.builder().direction("Вычислительная техника").name("АВТ-501").build();
        group = createRecord(group, sessionFactory);

        Student vasyaStudent = Student.builder()
                .name("Вася")
                .group(group)
                .contact(ContactInfo.builder()
                        .email("some@gmail.ru")
                        .telephoneNumber("xxxxxxxx")
                        .build())
                .build();
        createRecord(vasyaStudent, sessionFactory);

        Student petyaStudent = Student.builder()
                .name("Петя")
                .group(group)
                .contact(ContactInfo.builder()
                        .email("some2@gmail.ru")
                        .telephoneNumber("xxxxxxxxy")
                        .build())
                .build();
        createRecord(petyaStudent, sessionFactory);

        Discipline math = Discipline.builder()
                .code(151)
                .description("Выч. мат")
                .build();
        Discipline philos = Discipline.builder()
                .code(152)
                .description("Философия")
                .build();
        Discipline bathroom = Discipline.builder()
                .code(153)
                .description("бассейн")
                .build();
        createRecord(math, sessionFactory);
        createRecord(philos, sessionFactory);
        createRecord(bathroom, sessionFactory);
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
}
