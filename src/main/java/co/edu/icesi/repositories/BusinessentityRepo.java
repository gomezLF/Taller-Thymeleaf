package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.person.Businessentity;

@Repository
public interface BusinessentityRepo extends CrudRepository<Businessentity, Long>{
	
	

}
