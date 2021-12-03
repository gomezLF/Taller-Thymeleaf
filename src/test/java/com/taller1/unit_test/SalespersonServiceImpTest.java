package com.taller1.unit_test;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.exception.LogicalException;
import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.person.Businessentity;
import co.edu.icesi.model.person.Person;
import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.BusinessentityRepo;
import co.edu.icesi.repositories.EmployeeRepo;
import co.edu.icesi.repositories.PersonRepo;
import co.edu.icesi.repositories.SalespersonRepo;
import co.edu.icesi.services.SalespersonService;
import co.edu.icesi.services.SalespersonServiceImp;

@ContextConfiguration(classes = SalespersonServiceImpTest.class)
@ExtendWith(MockitoExtension.class)
class SalespersonServiceImpTest {
	
	private static final int BUSINESSENTITY_ID = 8080; 
	
	private Salesperson salesperson;
	private Businessentity businessentity;
	private Employee employee;
	private Person person;
	
	@Mock
	private SalespersonRepo salespersonRepoMock;
	@Mock
	private BusinessentityRepo businessentityRepoMock;
	@Mock
	private EmployeeRepo employeeRepoMock;
	@Mock
	private PersonRepo personRepoMock;
	
	private SalespersonService salespersonService;
	
	
	@BeforeAll
	static void init() {
		System.out.println("\"---------------SalespersonService Test-----------------\"");
	}
	
	
	
	@BeforeEach
	void setUp() {
		salespersonService = new SalespersonServiceImp(salespersonRepoMock, businessentityRepoMock, employeeRepoMock, personRepoMock);
		salesperson = new Salesperson();
		businessentity = new Businessentity();
		employee = new Employee();
		person = new Person();
		
		businessentity.setBusinessentityid(BUSINESSENTITY_ID);
		salesperson.setBusinessentityid(businessentity.getBusinessentityid());
		employee.setBusinessentityid(businessentity.getBusinessentityid());
		person.setBusinessentityid(businessentity.getBusinessentityid());
		
		
		businessentity.setPerson(person);
		person.setBusinessentity(businessentity);
		
		Timestamp time = Timestamp.valueOf("2025-11-19 00:00:00.0");
		salesperson.setSalesquota(BigDecimal.TEN);
		salesperson.setModifieddate(LocalDate.now());
	}
	
	@Test
	void saveSalesPersonUnitTest() {
		
		when(salespersonRepoMock.findById((BUSINESSENTITY_ID))).thenReturn(Optional.of(salesperson));
		
		try {
			salespersonService.saveSalesPerson(salesperson);
		} catch (LogicalException e) {
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(salespersonService.findSalesperson(BUSINESSENTITY_ID).get().getBusinessentityid(), "El objeto Salesperson no esta agregado al repositorio");
		
		
	}
	
	@Test
	void editSalesPersonUnitTest() {
		when(salespersonRepoMock.findById((BUSINESSENTITY_ID))).thenReturn(Optional.of(salesperson));
		
		try {
			salespersonService.editSalesPerson(salesperson);
		} catch (LogicalException e) {
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(salespersonService.findSalesperson(BUSINESSENTITY_ID).get().getBusinessentityid(), "El objeto Salesperson no esta agregado al repositorio");
		
	}
	
	@AfterEach
	void tearDown() {
		salespersonService = null;
		salesperson = null;
		businessentity = null;
		employee = null;
		person = null;
		
		System.gc();
	}
	
	
	
	@AfterAll
	static void end() {
		System.out.println("\"---------------SalespersonService Tested-----------------\"");
	}
}
