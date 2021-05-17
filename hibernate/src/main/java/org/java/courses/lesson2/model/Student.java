package org.java.courses.lesson2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"disciplines"})
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_discipline",
            joinColumns = {@JoinColumn(name = "student")},
            inverseJoinColumns = {@JoinColumn(name="discipline")})
    @Builder.Default
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"group\"")
    private Group group;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact", nullable = false)
    private ContactInfo contact;
}
