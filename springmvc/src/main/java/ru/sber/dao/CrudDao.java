package ru.sber.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(Long id);

    void save(T model);

    List<T> findAll();
}
