package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Salesorderheader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
@Scope("singleton")
public class SalesorderheaderDaoImp implements Dao<Salesorderheader> {

    @PersistenceContext
    EntityManager entityManager;

    public SalesorderheaderDaoImp(){

    }

    @Override
    public Salesorderheader findById(Integer id) {
        return entityManager.find(Salesorderheader.class, id);
    }

    @Override
    @Transactional
    public void save(Salesorderheader salesorderheader) {
        entityManager.persist(salesorderheader);
    }

    @Override
    @Transactional
    public void update(Salesorderheader salesorderheader) {
        entityManager.merge(salesorderheader);
    }

    @Override
    @Transactional
    public void delete(Salesorderheader salesorderheader) {
        entityManager.remove(salesorderheader);
    }

    @Override
    public List<Salesorderheader> findAll() {
        String query = "SELECT s FROM Salesorderheader s";
        return entityManager.createQuery(query).getResultList();
    }

    public List<Salesorderheader> findAllByBussinesentityId(Long bussinesentityId) {
        Query query = entityManager.createQuery("SELECT s FROM Salesorderheader s WHERE s.salesperson.bussinesentityId = :bussinesentityId");
        query.setParameter("bussinesentityId", bussinesentityId);
        return query.getResultList();
    }

    public List<Salesorderheader> findAllBysalesorderid(Integer salesroderid) {
        Query query = entityManager.createQuery("SELECT s FROM Salesorderheader s WHERE s.salesroderid = :salesroderid");
        query.setParameter("salesroderid", salesroderid);
        return query.getResultList();
    }

    public List<Salesorderheader> findAllByorderdate(LocalDate orderdate) {
        Query query = entityManager.createQuery("SELECT s FROM Salesorderheader s WHERE s.orderdate = :orderdate");
        query.setParameter("orderdate", orderdate);
        return query.getResultList();
    }
    public List<Salesorderheader> salesorderheaderAndSalesorderdetail(LocalDate orderdate, LocalDate modifieddate) {
        String search = "SELECT soh, SUM(soh.salesorderdetails.size) FROM Salesorderheader soh  INNER JOIN Salesorderdetail sod ON soh.salesorderid = sod.salesorderheader.salesorderid  WHERE soh.orderdate > :orderdate AND soh.modifieeddate < :modifieddate ORDER BY soh.orderdate";
        Query query = entityManager.createQuery(search);
        query.setParameter("orderdate", orderdate);
        query.setParameter("modifieddate", modifieddate);
        return query.getResultList();
    }

    public List<Salesorderheader> salesPersonsByOrderdate(LocalDate orderdate) {
        String search = "SELECT sper.salesorderheaders FROM Salesperson sper  INNER JOIN Salesorderheader soh  ON sper.bussinesentityid = soh.salesperson.bussinesentityid  WHERE soh.orderdate = :orderdate AND sper.salesorderheaders.size < 2";
        Query query = entityManager.createQuery(search);
        query.setParameter("orderdate", orderdate);
        return query.getResultList();
    }
}
