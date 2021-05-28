package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
}
