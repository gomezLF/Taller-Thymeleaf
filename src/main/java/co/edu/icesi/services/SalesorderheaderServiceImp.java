package co.edu.icesi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
	}
	
	@Override
	public Optional<Salesorderheader> findSalesorderheader(int id) {
		return salesorderheaderRepo.findById(id);
	}
	
	
	@Override
	public Iterable<Salesorderheader> findAll() {
		return salesorderheaderRepo.findAll();
	}

}
