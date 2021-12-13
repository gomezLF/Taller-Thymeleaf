package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Creditcard;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@Scope("singleton")
public class CreditcardDaoImp implements CreditcardDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Creditcard findById(Integer id) {
        return entityManager.find(Creditcard.class, id);
    }

    @Override
    @Transactional
    public void saveCreditcard(Creditcard creditcard) {
        entityManager.persist(creditcard);
    }

    @Override
    @Transactional
    public void updateCreditcard(Creditcard creditcard) {
        entityManager.merge(creditcard);
    }

    @Override
    @Transactional
    public void deleteCreditcard(Creditcard creditcard) {
        entityManager.remove(creditcard);
    }

    @Override
    @Transactional
    public Optional<Creditcard> findAll() {
        String query = "SELECT c FROM Creditcard c";
        return entityManager.createQuery(query).getResultList();
    }
}
