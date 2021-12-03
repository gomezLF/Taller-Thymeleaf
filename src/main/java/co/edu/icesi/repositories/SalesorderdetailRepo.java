package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.sales.Salesorderdetail;

@Repository
public interface SalesorderdetailRepo extends CrudRepository<Salesorderdetail, Integer> {

}
