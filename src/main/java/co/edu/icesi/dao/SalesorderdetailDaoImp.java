package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Salesorderdetail;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public void delete(Salesorderdetail salesorderdetail) {
        entityManager.remove(salesorderdetail);
    }

    @Override
    public List<Salesorderdetail> findAll() {
        String query = "SELECT s FROM Salesorderdetail s";
        return entityManager.createQuery(query).getResultList();
    }

    
}
