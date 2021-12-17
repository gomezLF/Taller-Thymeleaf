package co.edu.icesi.services;

import java.time.LocalDate;
import java.util.Optional;

import co.edu.icesi.dao.SalespersonDaoImp;
import org.springframework.stereotype.Service;

import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.BusinessentityRepo;
import co.edu.icesi.repositories.EmployeeRepo;
import co.edu.icesi.repositories.PersonRepo;
import co.edu.icesi.repositories.SalespersonRepo;

@Service
public class SalespersonServiceImp implements SalespersonService {
	
	SalespersonDaoImp salespersonDaoImp;
	
	
	public SalespersonServiceImp(SalespersonDaoImp salespersonDaoImp) {
		this.salespersonDaoImp = salespersonDaoImp;
	}
	
	@Override
	public void saveSalesPerson(Salesperson salesPerson) {
		salespersonDaoImp.save(salesPerson);
	}
	
	@Override
	public void editSalesPerson(Salesperson salesPerson) {
		Optional<Salesperson> temp = findSalesperson(salesPerson.getBusinessentityid());
		
		if(temp.isPresent()) {
			temp.get().setBusinessentityid(salesPerson.getBusinessentityid());
			temp.get().setBonus(salesPerson.getBonus());
			temp.get().setCommissionpct(salesPerson.getCommissionpct());
			temp.get().setRowguid(salesPerson.getRowguid());
			temp.get().setSaleslastyear(salesPerson.getSaleslastyear());
			temp.get().setSalesquota(salesPerson.getSalesquota());
			temp.get().setSalesytd(salesPerson.getSalesytd());
			temp.get().setEmployee(salesPerson.getEmployee());
			temp.get().setModifieddate(LocalDate.now());

			salespersonDaoImp.update(temp.get());
		}
	}
	
	@Override
	public Optional<Salesperson> findSalesperson(int id) {
		return Optional.ofNullable(salespersonDaoImp.findById(id));
	}

	@Override
	public Iterable<Salesperson> findAll() {
		return salespersonDaoImp.findAll();
	}

	public void deleteSalesperson(Salesperson salesperson){
		salespersonDaoImp.delete(salesperson);
	}
}
