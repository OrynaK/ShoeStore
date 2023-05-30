package ua.nure.shoestore.dao;

import java.util.List;

public interface CRUDRepository<T> {
    long insert(T entity) throws DBException;
    void update(T entity);
    void delete(long id);
    List<T> findAll();
    T findById(long id);
}
