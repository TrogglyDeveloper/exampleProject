package com.troggly.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vlad on 26.07.2017.
 */
public interface GeneralService<T, PK extends Serializable> {

    public T save(T entity);

    public void delete(PK pk);

    public T findOne(PK pk);

    public List<T> findAll();

    public boolean exists(PK pk);


}
