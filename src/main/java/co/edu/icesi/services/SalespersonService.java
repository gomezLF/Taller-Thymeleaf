package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Salesperson;

@Service
public interface SalespersonService {
	
	void saveSalesPerson(Salesperson salesPerson) throws LogicalException;
	void editSalesPerson(Salesperson salesPerson) throws LogicalException;
	Optional<Salesperson> findSalesperson(int id);
	Iterable<Salesperson> findAll();
}
