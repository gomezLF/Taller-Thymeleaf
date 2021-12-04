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

@Repository
@Scope("singleton")
public class CreditcardDao implements Dao<Creditcard> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public CreditcardDao() {
		
	}

	@Override
	public Optional<Creditcard> get(Integer id) {
		return Optional.ofNullable(entityManager.find(Creditcard.class, id));
	}
	
	public Optional<Creditcard> findById(Integer id) {
		return Optional.ofNullable(entityManager.find(Creditcard.class, id));
	}
	
	@Override
	public List<Creditcard> getAll() {
		Query query = entityManager.createQuery("SELECT c FROM Creditcard c");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Creditcard creditcard) {
		executeInsideTransaction(entityManager -> entityManager.persist(creditcard));
	}

	@Override
	@Transactional
	public void update(Creditcard creditrcard) {
		executeInsideTransaction(entityManager -> entityManager.merge(creditrcard));
	}

	@Override
	@Transactional
	public void deleteById(Integer creditcardid) {
		Creditcard creditcard = get(creditcardid).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(creditcard));
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
		Query query = entityManager.createQuery("SELECT c FROM Creditcard c WHERE c.bussinesentity.bussinesentityId = :bussinesentityId");
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

}
