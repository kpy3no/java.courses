package ru.sber.dao;

import ru.sber.model.User;

import java.util.List;

public interface UserDao extends CrudDao<User> {

    List<User> findAllByFirstName(String firstName);
}
