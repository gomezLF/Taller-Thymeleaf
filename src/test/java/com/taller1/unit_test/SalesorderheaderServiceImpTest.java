package com.taller1.unit_test;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
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
import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.CreditcardRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;
import co.edu.icesi.repositories.SalespersonRepo;
import co.edu.icesi.services.SalesorderheaderService;
import co.edu.icesi.services.SalesorderheaderServiceImp;

@ContextConfiguration(classes = SalesorderheaderServiceImpTest.class)
@ExtendWith(MockitoExtension.class)
class SalesorderheaderServiceImpTest {
	
	private static final int SALESORDERHEADER_ID = 6666;
	private static final int CREDITCARD_ID = 2020;
	private static final int SALESPERSON_ID = 8080;
	
	private Salesperson salesperson;
	private Creditcard creditcard;
	private Salesorderheader salesorderheader;
	
	@Mock
	private SalespersonRepo salespersonRepo;
	@Mock
	private CreditcardRepo creditcardRepo;
	@Mock
	private SalesorderheaderRepo salesorderheaderRepo;
	
	private SalesorderheaderService salesorderheaderService;
	
	
	@BeforeAll
	static void init() {
		System.out.println("\"---------------SalesorderheaderService Test-----------------\"");
	}
	
	
	@BeforeEach
	void setUp() {
		salesorderheaderService = new SalesorderheaderServiceImp(salespersonRepo, creditcardRepo, salesorderheaderRepo);
		salesperson = new Salesperson();
		creditcard = new Creditcard();
		salesorderheader = new Salesorderheader();
		
		salesperson.setBusinessentityid(SALESPERSON_ID);
		creditcard.setCreditcardid(CREDITCARD_ID);
		salesorderheader.setSalesorderid(SALESORDERHEADER_ID);
		
		salesorderheader.setCreditcard(creditcard);
		salesorderheader.setSalesperson(salesperson);
		creditcard.addSalesorderheader(salesorderheader);
		salesperson.addSalesorderheader(salesorderheader);
		
		Timestamp time = Timestamp.valueOf("2025-11-19 00:00:00.0");
		//salesorderheader.setShipdate(time);
		salesorderheader.setSubtotal(BigDecimal.TEN);
		//salesorderheader.setModifieddate(Timestamp.from(Instant.now()));
	}
	
	@Test
	void saveSalesOrderHeaderTest() {
		//salesorderheader.setOrderdate(Timestamp.from(Instant.now()));
		
		when(salesorderheaderRepo.findById((SALESORDERHEADER_ID))).thenReturn(Optional.of(salesorderheader));
		when(creditcardRepo.findById((CREDITCARD_ID))).thenReturn(Optional.of(creditcard));
		when(salespersonRepo.findById((SALESPERSON_ID))).thenReturn(Optional.of(salesperson));
		
		try {
			salesorderheaderService.saveSalesOrderHeader(salesorderheader);
		} catch (LogicalException e) {
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(salesorderheaderService.findSalesorderheader(SALESORDERHEADER_ID).get().getSalesorderid(), "El objeto Salesorderheader no esta agregado al repositorio");
	
	}
	
	@Test
	void editSalesOrderHeader() {
		when(salesorderheaderRepo.findById((SALESORDERHEADER_ID))).thenReturn(Optional.of(salesorderheader));
		when(creditcardRepo.findById((CREDITCARD_ID))).thenReturn(Optional.of(creditcard));
		when(salespersonRepo.findById((SALESPERSON_ID))).thenReturn(Optional.of(salesperson));
		
		try {
			salesorderheaderService.saveSalesOrderHeader(salesorderheader);
		} catch (LogicalException e) {
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(salesorderheaderService.findSalesorderheader(SALESORDERHEADER_ID).get().getSalesorderid(), "El objeto Salesorderheader no esta agregado al repositorio");
	
	}
	
	@AfterEach
	void tearDown() {
		salesorderheaderService = null;
		salesperson = null;
		creditcard = null;
		salesorderheader = null;
		
		System.gc();
	}
	
	
	@AfterAll
	static void end() {
		System.out.println("\"---------------SalesorderheaderService Tested-----------------\"");
	}
}
