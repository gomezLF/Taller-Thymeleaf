package co.edu.icesi.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.repositories.CreditcardRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;
import co.edu.icesi.repositories.SalespersonRepo;

@Service
public class SalesorderheaderServiceImp implements SalesorderheaderService {
	
	SalespersonRepo salespersonRepo;
	CreditcardRepo creditcardRepo;
	SalesorderheaderRepo salesorderheaderRepo;
	
	
	public SalesorderheaderServiceImp(SalespersonRepo salespersonRepo, CreditcardRepo creditcardRepo, SalesorderheaderRepo salesorderheaderRepo) {
		this.salespersonRepo = salespersonRepo;
		this.creditcardRepo = creditcardRepo;
		this.salesorderheaderRepo = salesorderheaderRepo;
	}

	@Override
	public void saveSalesOrderHeader(Salesorderheader salesorderheader){
		salesorderheaderRepo.save(salesorderheader);
		
		/**
		if(salespersonRepo.findById(salesorderheader.getSalesperson().getBusinessentityid()).isPresent()
				&& creditcardRepo.findById(salesorderheader.getCreditcard().getCreditcardid()).isPresent()
				&& !salesorderheaderRepo.existsById(salesorderheader.getSalesorderid())) 
		{
			validateSaveConstraints(salesorderheader);
			validateConstraints(salesorderheader);
			
		}*/
	}

	@Override
	public void editSalesOrderHeader(Salesorderheader salesorderheader){
		Optional<Salesorderheader> temp = findSalesorderheader(salesorderheader.getSalesorderid());
		
		if(temp.isPresent()) {
			temp.get().setAccountnumber(salesorderheader.getAccountnumber());
			temp.get().setModifieddate(LocalDate.now());
			temp.get().setOrderdate(salesorderheader.getOrderdate());
			temp.get().setShipdate(salesorderheader.getShipdate());
			temp.get().setSubtotal(salesorderheader.getSubtotal());
			
			salesorderheaderRepo.save(temp.get());
		}
		
		/**
		if(salespersonRepo.findById(salesorderheader.getSalesperson().getBusinessentityid()).isPresent()
				&& creditcardRepo.findById(salesorderheader.getCreditcard().getCreditcardid()).isPresent()
				&& salesorderheaderRepo.existsById(salesorderheader.getSalesorderid())) 
		{
			validateConstraints(salesorderheader);
			salesorderheaderRepo.save(salesorderheader);
		}*/
	}
	
	@Override
	public Optional<Salesorderheader> findSalesorderheader(int id) {
		return salesorderheaderRepo.findById(id);
	}
	
	private void validateSaveConstraints(Salesorderheader salesorderheader) throws LogicalException{
		if(salesorderheader.getOrderdate().equals(Timestamp.from(Instant.now()))) {
			throw new LogicalException("La fecha de la orden es diferente a la fecha actual");
		}
	}
	
	/**
	private void validateConstraints(Salesorderheader salesorderheader) throws LogicalException{
		if(salesorderheader.getShipdate().before(Timestamp.valueOf(LocalDate.now().atStartOfDay()))) {
			throw new LogicalException("La fecha de envío es igual o previa a la actual");
			
		}else if(salesorderheader.getSubtotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new LogicalException("El subtotal de la orden de compra es menor o igual a cero");
			
		}else if(!salesorderheader.getModifieddate().equals(Timestamp.valueOf(LocalDate.now().atStartOfDay()))) {
			System.out.println("Fecha de Modificación: " + salesorderheader.getModifieddate());
			System.out.println("Fecha actual: " + Timestamp.from(Instant.now()));
			
			throw new LogicalException("La fecha de modificación de la orden de compra no es la misma fecha actual del sistema");
		}
	}
	*/
	@Override
	public Iterable<Salesorderheader> findAll() {
		return salesorderheaderRepo.findAll();
	}

}
