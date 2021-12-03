package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Salesorderheader;

@Service
public interface SalesorderheaderService {

	void saveSalesOrderHeader(Salesorderheader salesorderheader) throws LogicalException;
	void editSalesOrderHeader(Salesorderheader salesorderheader) throws LogicalException;
	Optional<Salesorderheader> findSalesorderheader(int id);
	Iterable<Salesorderheader> findAll();
}
