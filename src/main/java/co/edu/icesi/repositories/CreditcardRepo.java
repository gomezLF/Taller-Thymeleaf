package co.edu.icesi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.model.sales.Creditcard;

@Repository
public interface CreditcardRepo extends CrudRepository<Creditcard, Integer> {

}
