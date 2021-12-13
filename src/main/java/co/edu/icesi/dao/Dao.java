package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Creditcard;

import java.util.List;

public interface Dao {
    Creditcard findById(Integer id);
    void save(Creditcard creditcard);
    void update(Creditcard creditcard);
    void delete(Creditcard creditcard);
    List<Creditcard> findAll();
}
