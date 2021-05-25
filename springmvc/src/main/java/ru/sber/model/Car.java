package ru.sber.model;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "owner")
public class Car {
    private long id;
    private String model;
    private User owner;
}
