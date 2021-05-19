package org.java.courses.lesson2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.java.courses.lesson2.Identified;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"disciplines"})
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "student")
@NamedQueries({
        @NamedQuery(name = "Student.findAll", query = "select s from Student s"),
        @NamedQuery(name = "Student.findById",
                query = "select distinct s from Student s left join fetch s.disciplines d where s.id = :id"),
        @NamedQuery(name="Student.findAllWithDisciplines",
                query="select s from Student s left join fetch s.disciplines d"),
        @NamedQuery(name="Student.findAllWithContact",
                query="select s from Student s inner join fetch s.contact c")
})
@SqlResultSetMapping(
        name = "nativeSqlResult",
        entities = @EntityResult(entityClass = Student.class)
)
public class Student implements Identified {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_discipline",
            joinColumns = {@JoinColumn(name = "student")},
            inverseJoinColumns = {@JoinColumn(name="discipline")})
    @Builder.Default
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "\"group\"")
    private Group group;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact", nullable = false)
    private ContactInfo contact;
}
