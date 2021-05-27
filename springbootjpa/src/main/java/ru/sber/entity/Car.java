package ru.sber.entity;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "car")
@Entity
@ToString(exclude = "owner")
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "model")
    private String model;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Car(long id) {
        this.id = id;
    }
}




