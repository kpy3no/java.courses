package org.java.courses.lesson2.dao;

import lombok.RequiredArgsConstructor;
import org.java.courses.lesson2.Identified;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.function.Function;

@RequiredArgsConstructor
public class SharedDao<T extends Identified> {
    private final Class<T> clazz;
    private final EntityManager entityManager;

    public T save(T entity) {
        if (isNew(entity)) {
            return withEmAndTransaction((entityManager) -> {
                entityManager.persist(entity);
                return entity;
            });
        }

        return withEmAndTransaction((entityManager) -> entityManager.merge(entity));
    }

    public T findById(Long id) {
        return withEm(entityManager -> entityManager.find(clazz, id));
    }

    protected boolean isNew(T entity) {
        return entity.getId() == null;
    }

    protected <S> S withEmAndTransaction(Function<EntityManager, S> function) {
        return withEm(entityManager -> {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            S returnValue = function.apply(entityManager);

            transaction.commit();
            return returnValue;
        });
    }

    protected <S> S withEm(Function<EntityManager, S> function) {
        return function.apply(entityManager);
    }

    public void clear() {
        if (entityManager != null) {
            entityManager.clear();
        }
    }
}
