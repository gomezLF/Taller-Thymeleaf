package co.edu.icesi.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.BusinessentityRepo;
import co.edu.icesi.repositories.EmployeeRepo;
import co.edu.icesi.repositories.PersonRepo;
import co.edu.icesi.repositories.SalespersonRepo;

@Service
public class SalespersonServiceImp implements SalespersonService {
	
	SalespersonRepo salespersonRepo;
	BusinessentityRepo businessentityRepo;
	EmployeeRepo employeeRepo;
	PersonRepo personRepo;
	
	
	public SalespersonServiceImp(SalespersonRepo salespersonRepo, BusinessentityRepo businessentityRepo, EmployeeRepo employeeRepo, PersonRepo personRepo) {
		this.salespersonRepo = salespersonRepo;
		this.businessentityRepo = businessentityRepo;
		this.employeeRepo = employeeRepo;
		this.personRepo = personRepo;
	}
	
	@Override
	public void saveSalesPerson(Salesperson salesPerson) {
		salespersonRepo.save(salesPerson);
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
			temp.get().setModifieddate(LocalDate.now());
			
			salespersonRepo.save(temp.get());
		}
	}
	
	@Override
	public Optional<Salesperson> findSalesperson(int id) {
		return salespersonRepo.findById(id);
	}

	@Override
	public Iterable<Salesperson> findAll() {
		return salespersonRepo.findAll();
	}
}
