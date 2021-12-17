package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Salesorderdetail;
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
public class SalesorderdetailDaoImp implements Dao<Salesorderdetail>{

    @PersistenceContext
    EntityManager entityManager;

    public SalesorderdetailDaoImp(){

    }

    @Override
    public Salesorderdetail findById(Integer id) {
        return entityManager.find(Salesorderdetail.class, id);
    }

    @Override
    @Transactional
    public void save(Salesorderdetail salesorderdetail) {
        entityManager.persist(salesorderdetail);
    }

    @Override
    @Transactional
    public void update(Salesorderdetail salesorderdetail) {
        entityManager.merge(salesorderdetail);
    }

    @Override
    @Transactional
    public void delete(@NotNull Salesorderdetail salesorderdetail) {
        Optional<Salesorderdetail> optional = Optional.ofNullable(findById(salesorderdetail.getId()));

        optional.ifPresent(value -> executeInsideTransaction(entityManager -> entityManager.remove(value)));

    }

    @Override
    public List<Salesorderdetail> findAll() {
        String query = "SELECT s FROM Salesorderdetail s";
        return entityManager.createQuery(query).getResultList();
    }

    public List<Salesorderdetail> findAllByBussinesentityId(long bussinesentityId){
        Query query = entityManager.createQuery("SELECT s FROM Salesorderheader s WHERE s.salesorderheader.salesperson.bussinesentityId = :bussinesentityId");
        query.setParameter("bussinesentityId", bussinesentityId);
        return query.getResultList();
    }

    public List<Salesorderdetail> findAllBySalesHeader(int salesorderId){
        Query query = entityManager.createQuery("SELECT s FROM Salesorderheader s WHERE s.salesorderheader.salesorderId = :salesorderId");
        query.setParameter("salesorderId", salesorderId);
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
