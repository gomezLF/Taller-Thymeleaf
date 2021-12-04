package co.edu.icesi.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.model.sales.Salesorderheader;

@Repository
@Scope("singleton")
public class SalesorderheaderDao implements Dao<Salesorderheader> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SalesorderheaderDao() {
		
	}

	@Override
	public Optional<Salesorderheader> get(Integer id) {
		return Optional.ofNullable(entityManager.find(Salesorderheader.class, id));
	}
	
	public Optional<Salesorderheader> findById(Integer id) {
		return Optional.ofNullable(entityManager.find(Salesorderheader.class, id));
	}

	@Override
	public List<Salesorderheader> getAll() {
		Query query = entityManager.createQuery("SELECT s FROM Salesorderheader s");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Salesorderheader salesorderheader) {
		executeInsideTransaction(entityManager -> entityManager.persist(salesorderheader));		
	}

	@Override
	@Transactional
	public void update(Salesorderheader salesorderheader) {
		executeInsideTransaction(entityManager -> entityManager.persist(salesorderheader));		
	}

	@Override
	@Transactional
	public void deleteById(Integer salesorderid) {
		Salesorderheader soh = get(salesorderid).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(soh));		
	}
	
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		try {
			action.accept(entityManager);	 
		}
		catch (RuntimeException e) {
			throw e;
		}
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
