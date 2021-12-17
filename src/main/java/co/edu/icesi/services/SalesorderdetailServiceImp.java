package co.edu.icesi.services;

import java.time.LocalDate;
import java.util.Optional;

import co.edu.icesi.dao.SalesorderdetailDaoImp;
import org.springframework.stereotype.Service;

import co.edu.icesi.model.sales.Salesorderdetail;
import co.edu.icesi.repositories.SalesorderdetailRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;

@Service
public class SalesorderdetailServiceImp implements SalesorderdetailService {

	private SalesorderdetailDaoImp salesorderdetailDaoImp;
	
	
	public SalesorderdetailServiceImp(SalesorderdetailDaoImp salesorderdetailDaoImp) {
		this.salesorderdetailDaoImp = salesorderdetailDaoImp;
	}

	@Override
	public void saveSalesOrderDetails(Salesorderdetail salesorderdetail) {
		salesorderdetailDaoImp.save(salesorderdetail);
	}

	@Override
	public void editSalesOrderDetails(Salesorderdetail salesorderdetail) {
		Optional<Salesorderdetail> temp = findSalesorderdetail(salesorderdetail.getId());
		
		if(temp.isPresent()) {
			temp.get().setCarriertrackingnumber(salesorderdetail.getCarriertrackingnumber());
			temp.get().setOrderqty(salesorderdetail.getOrderqty());
			temp.get().setRowguid(salesorderdetail.getRowguid());
			temp.get().setUnitprice(salesorderdetail.getUnitprice());
			temp.get().setUnitpricediscount(salesorderdetail.getUnitpricediscount());
			temp.get().setSalesorderheader(salesorderdetail.getSalesorderheader());
			temp.get().setModifieddate(LocalDate.now());

			salesorderdetailDaoImp.update(temp.get());
		}
	}
	
	@Override
	public Optional<Salesorderdetail> findSalesorderdetail(int id) {
		return Optional.ofNullable(salesorderdetailDaoImp.findById(id));
	}

	@Override
	public Iterable<Salesorderdetail> findAll() {
		return salesorderdetailDaoImp.findAll();
	}

	public void deleteSalesorderdetail(Salesorderdetail salesorderdetail){
		salesorderdetailDaoImp.delete(salesorderdetail);
	}
}
