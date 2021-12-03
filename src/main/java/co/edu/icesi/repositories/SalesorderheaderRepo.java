package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.sales.Salesorderheader;

@Repository
public interface SalesorderheaderRepo extends CrudRepository<Salesorderheader, Integer>{

}
