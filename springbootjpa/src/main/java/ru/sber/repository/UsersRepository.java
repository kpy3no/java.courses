package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.entity.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {

    List<User> findFirstByFirstNameAndLastName(String firstName, String lastName);
}
