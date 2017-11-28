package com.troggly.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vlad on 26.07.2017.
 */
public abstract class GeneralServiceImp<T,PK extends Serializable> implements GeneralService<T,PK> {
    public abstract JpaRepository<T,PK> getRepository();

    @Transactional
    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(PK pk) {
        getRepository().delete(pk);
    }

    @Override
    public T findOne(PK pk) {
        return getRepository().findOne(pk);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public boolean exists(PK pk) {
        return getRepository().exists(pk);
    }
}
