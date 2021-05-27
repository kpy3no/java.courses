package ru.sber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entity.User;
import ru.sber.repository.UsersRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public void save(User user) {
        usersRepository.save(user);
    }

    public List<User> test() {
        return usersRepository.findFirstByFirstNameAndLastName("Sasha", "Kuzmin");
    }
}