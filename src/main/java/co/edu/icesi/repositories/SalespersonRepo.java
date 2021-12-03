package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.sales.Salesperson;

@Repository
public interface SalespersonRepo extends CrudRepository	<Salesperson, Integer>{	
	

}
