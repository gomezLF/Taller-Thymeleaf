package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Creditcard;

import java.util.Optional;

public interface CreditcardDao {
    Creditcard findById(Integer id);
    void saveCreditcard(Creditcard creditcard);
    void updateCreditcard(Creditcard creditcard);
    void deleteCreditcard(Creditcard creditcard);
    Optional<Creditcard> findAll();
}
