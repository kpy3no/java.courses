package ru.sber.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    private String userName;
    private String lastName;
    List<Car> cars;
}
