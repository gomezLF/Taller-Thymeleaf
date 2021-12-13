package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Creditcard;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    T findById(Integer id);
    void save(T t);
    void update(T t);
    void delete(T t);
    List<T> findAll();
}
