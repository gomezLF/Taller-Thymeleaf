package co.edu.icesi.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Salesorderdetail;
import co.edu.icesi.model.sales.SalesorderdetailPK;
import co.edu.icesi.repositories.SalesorderdetailRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;

@Service
public class SalesorderdetailServiceImp implements SalesorderdetailService {
	
	SalesorderheaderRepo salesorderheaderRepo;
	SalesorderdetailRepo salesorderdetailRepo;
	
	
	
	public SalesorderdetailServiceImp(SalesorderheaderRepo salesorderheaderRepo, SalesorderdetailRepo salesorderdetailRepo) {
		this.salesorderheaderRepo = salesorderheaderRepo;
		this.salesorderdetailRepo = salesorderdetailRepo;
	}

	@Override
	public void saveSalesOrderDetails(Salesorderdetail salesorderdetail) {
		salesorderdetailRepo.save(salesorderdetail);
		
		/**
		if(salesorderheaderRepo.existsById(salesorderdetail.getSalesorderheader().getSalesorderid())
				&& !salesorderdetailRepo.existsById(salesorderdetail.getId().getSalesorderdetailid())) 
		{
			//salesorderdetail.setId(new SalesorderdetailPK());
			//validateConstraints(salesorderdetail);
			
		}*/
	}

	@Override
	public void editSalesOrderDetails(Salesorderdetail salesorderdetail) {
		Optional<Salesorderdetail> temp = findSalesorderdetail(salesorderdetail.getId().getSalesorderdetailid());
		
		if(temp.isPresent()) {
			temp.get().setCarriertrackingnumber(salesorderdetail.getCarriertrackingnumber());
			temp.get().setOrderqty(salesorderdetail.getOrderqty());
			temp.get().setRowguid(salesorderdetail.getRowguid());
			temp.get().setUnitprice(salesorderdetail.getUnitprice());
			temp.get().setUnitpricediscount(salesorderdetail.getUnitpricediscount());
			
			salesorderdetailRepo.save(temp.get());
		}
		
		/**
		if(salesorderheaderRepo.existsById(salesorderdetail.getSalesorderheader().getSalesorderid())
				&& salesorderdetailRepo.existsById(salesorderdetail.getId().getSalesorderdetailid())) 
		{
			validateConstraints(salesorderdetail);
			salesorderdetailRepo.save(salesorderdetail);
		}*/
	}
	
	@Override
	public Optional<Salesorderdetail> findSalesorderdetail(int id) {
		return salesorderdetailRepo.findById(id);
	}
	
	private void validateConstraints(Salesorderdetail salesorderdetail) throws LogicalException{
		if(salesorderdetail.getUnitprice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new LogicalException("El precio unitario de los detalles de la orden es menor o igual a cero.");
			
		}else if(salesorderdetail.getUnitprice().compareTo(BigDecimal.ZERO) < 0) {
			throw new LogicalException("El descuento unitario de los detalles de la orden es menor a cero"); 
			
		}else if(calculateStandardPrice(salesorderdetail.getUnitprice(), salesorderdetail.getOrderqty()).compareTo(BigDecimal.ZERO) <= 0) {
			throw new LogicalException("El precio estandar de los detalles de la orden es menor o igual a cero");
		}
	}
	
	private BigDecimal calculateStandardPrice(BigDecimal unitprice, int orderqty) {
		BigDecimal totalcost = BigDecimal.ZERO;
		BigDecimal itemcost = BigDecimal.ZERO;
		
		itemcost = unitprice.multiply(BigDecimal.valueOf(orderqty));
		totalcost = totalcost.add(itemcost);
		
		return totalcost;
	}

	@Override
	public Iterable<Salesorderdetail> findAll() {
		return salesorderdetailRepo.findAll();
	}

}
