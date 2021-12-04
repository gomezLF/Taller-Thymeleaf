package co.edu.icesi.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.Salesorderdetail;

@Repository
@Scope("singleton")
public class SalesorderdetailDao implements Dao<Salesorderdetail> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SalesorderdetailDao() {
		
	}

	@Override
	public Optional<Salesorderdetail> get(Integer id) {
		return Optional.ofNullable(entityManager.find(Salesorderdetail.class, id));
	}
	
	public Optional<Salesorderdetail> findById(Integer id) {
		return Optional.ofNullable(entityManager.find(Salesorderdetail.class, id));
	}

	@Override
	public List<Salesorderdetail> getAll() {
		Query query = entityManager.createQuery("SELECT s FROM Salesorderdetail s");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Salesorderdetail salesorderdetail) {
		executeInsideTransaction(entityManager -> entityManager.persist(salesorderdetail));
	}

	@Override
	@Transactional
	public void update(Salesorderdetail salesorderdetail) {
		executeInsideTransaction(entityManager -> entityManager.persist(salesorderdetail));		
	}

	@Override
	@Transactional
	public void deleteById(Integer sodId) {
		Salesorderdetail sod = get(sodId).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(sod));
	}
	
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		try {
			action.accept(entityManager);	 
		}
		catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<Creditcard> findAllByBussinesentityId(Long bussinesentityId) {
		Query query = entityManager.createQuery("SELECT s FROM Salesroderdetail s WHERE s.salesorderheader.salesperson.bussinesentityId = :bussinesentityId");
		query.setParameter("bussinesentityId", bussinesentityId);
		return query.getResultList();
	}
	
	public List<Creditcard> findAllByExpmonth(Integer expmonth) {
		Query query = entityManager.createQuery("SELECT s FROM Creditcard s WHERE s.expmonth = :expmonth");
		query.setParameter("expmonth", expmonth);
		return query.getResultList();
	}
	
	public List<Creditcard> findAllByExpyear(Integer expyear) {
		Query query = entityManager.createQuery("SELECT s FROM Autotransition s WHERE s.expyear = :expyear");
		query.setParameter("expyear", expyear);
		return query.getResultList();
	}


}
