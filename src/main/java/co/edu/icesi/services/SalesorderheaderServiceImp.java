package co.edu.icesi.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import co.edu.icesi.dao.CreditcardDaoImp;
import co.edu.icesi.dao.SalesorderheaderDaoImp;
import co.edu.icesi.dao.SalespersonDaoImp;
import org.springframework.stereotype.Service;

import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.repositories.CreditcardRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;
import co.edu.icesi.repositories.SalespersonRepo;

@Service
public class SalesorderheaderServiceImp implements SalesorderheaderService {

	private SalesorderheaderDaoImp salesorderheaderDao;
	private CreditcardDaoImp creditcardDao;
	private SalespersonDaoImp salespersonDao;
	
	
	public SalesorderheaderServiceImp(SalesorderheaderDaoImp salesorderheaderDao, CreditcardDaoImp creditcardDao, SalespersonDaoImp salespersonDao) {
		this.salesorderheaderDao = salesorderheaderDao;
		this.creditcardDao = creditcardDao;
		this.salespersonDao = salespersonDao;
	}

	@Override
	public void saveSalesOrderHeader(Salesorderheader salesorderheader){
		salesorderheaderDao.save(salesorderheader);
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

			salesorderheaderDao.update(temp.get());
		}
	}
	
	@Override
	public Optional<Salesorderheader> findSalesorderheader(int id) {
		return Optional.ofNullable(salesorderheaderDao.findById(id));
	}

	@Override
	public Iterable<Salesorderheader> findAll() {
		return salesorderheaderDao.findAll();
	}

	public void deleteSalesorderheader(Salesorderheader salesorderheader){
		salesorderheaderDao.delete(salesorderheader);
	}

	public List<Salesorderheader> findAllByBussinesentityId(Long bussinesentityId) {
		return salesorderheaderDao.findAllByBussinesentityId(bussinesentityId);
	}

	public List<Salesorderheader> findAllBysalesorderid(Integer salesroderid){
		return salesorderheaderDao.findAllBysalesorderid(salesroderid);
	}

	public List<Salesorderheader> findAllByorderdate(LocalDate orderdate) {
		return salesorderheaderDao.findAllByorderdate(orderdate);
	}

	public List<Salesorderheader> salesorderheaderAndSalesorderdetail(LocalDate orderdate, LocalDate modifieddate) {
		return salesorderheaderDao.salesorderheaderAndSalesorderdetail(orderdate, modifieddate);
	}

	public List<Salesorderheader> salesPersonsByOrderdate(LocalDate orderdate) {
		return salesorderheaderDao.salesPersonsByOrderdate(orderdate);
	}
}
