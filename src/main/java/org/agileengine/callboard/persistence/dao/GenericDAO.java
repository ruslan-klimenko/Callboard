package org.agileengine.callboard.persistence.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    T create(T obj);
    T update(T obj);
    T remove(T obj);
    T findById(PK id);
    List<T> findAll();
    T findLast();
}
