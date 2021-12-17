package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Creditcard;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@Scope("singleton")
public class CreditcardDaoImp implements Dao<Creditcard> {

    @PersistenceContext
    EntityManager entityManager;

    public CreditcardDaoImp(){

    }

    @Override
    public Creditcard findById(Integer id) {
        return entityManager.find(Creditcard.class, id);
    }

    @Override
    @Transactional
    public void save(Creditcard creditcard) {
        entityManager.persist(creditcard);
    }

    @Override
    @Transactional
    public void update(Creditcard creditcard) {
        entityManager.merge(creditcard);
    }

    @Override
    @Transactional
    public void delete(@NotNull Creditcard creditcard) {
        Optional<Creditcard> optional = Optional.ofNullable(findById(creditcard.getCreditcardid()));

        optional.ifPresent(value -> executeInsideTransaction(entityManager -> entityManager.remove(value)));
    }

    @Override
    public List<Creditcard> findAll() {
        String query = "SELECT c FROM Creditcard c";
        return entityManager.createQuery(query).getResultList();
    }

    public List<Creditcard> findAllByBussinesentityId(Long bussinesentityId) {
        Query query = entityManager.createQuery("SELECT c FROM Creditcard c WHERE c.salesorderheaders.salesperson.bussinesentityId = :bussinesentityId");
        query.setParameter("bussinesentityId", bussinesentityId);
        return query.getResultList();
    }

    public List<Creditcard> findAllByExpmonth(Integer expmonth) {
        Query query = entityManager.createQuery("SELECT c FROM Creditcard c WHERE c.expmonth = :expmonth");
        query.setParameter("expmonth", expmonth);
        return query.getResultList();
    }

    public List<Creditcard> findAllByExpyear(Integer expyear) {
        Query query = entityManager.createQuery("SELECT c FROM Autotransition c WHERE c.expyear = :expyear");
        query.setParameter("expyear", expyear);
        return query.getResultList();
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        try {
            action.accept(entityManager);
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
}
