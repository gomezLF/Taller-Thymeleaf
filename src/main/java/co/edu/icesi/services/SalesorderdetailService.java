package co.edu.icesi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Salesorderdetail;

@Service
public interface SalesorderdetailService {
	
	void saveSalesOrderDetails(Salesorderdetail salesorderdetail) throws LogicalException;
	void editSalesOrderDetails(Salesorderdetail salesorderdetail) throws LogicalException;
	Optional<Salesorderdetail> findSalesorderdetail(int id);
	Iterable<Salesorderdetail> findAll();
}
