package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Creditcard;

@Service
public interface CreditcardService {
	void saveCreditCard(Creditcard creditcard) throws LogicalException;
	void editCreditCard(Creditcard creditcard) throws LogicalException;
	Optional<Creditcard> findCreditCard(int id);
	Iterable<Creditcard> findAll();
}
