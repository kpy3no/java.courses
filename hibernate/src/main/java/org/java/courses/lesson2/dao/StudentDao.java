package org.java.courses.lesson2.dao;

import org.java.courses.lesson2.model.ContactInfo;
import org.java.courses.lesson2.model.Discipline;
import org.java.courses.lesson2.model.Group;
import org.java.courses.lesson2.model.Student;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentDao extends Dao<Student> {
    public StudentDao(EntityManagerFactory emFactory) {
        super(emFactory, Student.class);
    }

    public List<Student> findAll() {
        return withEm(entityManager -> entityManager
                .createQuery("select student from org.java.courses.lesson2.model.Student student ", Student.class)
                .getResultList());
    }

    public List<Student> findAllByGroup(Group group) {
        return withEm(entityManager -> entityManager
                .createQuery("select student from org.java.courses.lesson2.model.Student student" +
                        " join student.group g on g.name = :group_name", Student.class)
                .setParameter("group_name", group.getName())
                .getResultList());
    }

    public List<Student> findAllNamedQuery() {
        return withEm(entityManager -> entityManager.createNamedQuery("Student.findAll", Student.class).getResultList());
    }

    public List<Student> findAllWithDisciplinesNamedQuery() {
        return withEm(entityManager -> entityManager.createNamedQuery("Student.findAllWithDisciplines", Student.class).getResultList());
    }

    public List<Student> findAllWithContactNamedQuery() {
        return withEm(entityManager -> entityManager.createNamedQuery("Student.findAllWithContact", Student.class).getResultList());
    }

    public List<Student> findByIdNamedQuery(Long id) {
        return withEm(entityManager -> entityManager
                .createNamedQuery("Student.findById", Student.class)
                .setParameter("id", id)
                .getResultList()
        );
    }

    public List<Student> findByCriteriaQuery(String name, String email) {
        return withEm(entityManager -> {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);
            Root<Student> root = criteriaQuery.from(Student.class);
            root.fetch("disciplines", JoinType.LEFT);

            criteriaQuery.select(root).distinct(true);

            Predicate criteria = cb.conjunction();
            if (name != null) {
                Predicate p = cb.equal(root.get("name"), name);
                criteria = cb.and(criteria, p);
            }
            if (email != null) {
                Predicate p = cb.equal(root.join("contact").get("email"), email);
                criteria = cb.and(criteria, p);
            }

            criteriaQuery.where(criteria);
            return entityManager.createQuery(criteriaQuery).getResultList();
        });
    }

    public List<Student> findByCriteriaQuery(String name, String email, Set<Integer> disciplineCodes) {
        return withEm(entityManager -> {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);
            Root<Student> root = criteriaQuery.from(Student.class);
            root.fetch("disciplines", JoinType.LEFT);

            criteriaQuery.select(root).distinct(true);

            Predicate criteria = cb.conjunction();
            if (name != null) {
                Predicate p = cb.equal(root.get("name"), name);
                criteria = cb.and(criteria, p);
            }
            if (email != null) {
                Predicate p = cb.equal(root.join("contact").get("email"), email);
                criteria = cb.and(criteria, p);
            }

            if (disciplineCodes != null && !disciplineCodes.isEmpty()) {
                Predicate p = cb.and(root.join("disciplines").get("code").in(disciplineCodes));
                criteria = cb.and(criteria, p);
            }

            criteriaQuery.where(criteria);
            List<Student> result = entityManager.createQuery(criteriaQuery).getResultList();
            return result;
        });
    }

    public List<Student> findWithContact() {
        return withEm(entityManager -> {
            Query query = entityManager.createNativeQuery("select * from student " +
                    "inner join contact_info ci on student.contact = ci.id", Student.class);
            return query.getResultList();
        });
    }

    public List<Student> findWithContactRaw() {
        List<?> result = withEm(entityManager -> {
            Query query = entityManager.createNativeQuery("select student.name as student_name, info.email as info_email from student " +
                    "inner join contact_info info on student.contact = info.id");
            return query.getResultList();
        });

        return result.stream().map(o -> {
            Object[] columns = (Object[]) o;
            return Student.builder()
                    .name((String) columns[0])
                    .contact(ContactInfo.builder().telephoneNumber((String) columns[1]).build())
                    .build();
        }).collect(Collectors.toList());
    }
}
