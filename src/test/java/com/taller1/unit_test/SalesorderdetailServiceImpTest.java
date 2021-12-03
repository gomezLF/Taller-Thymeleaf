package com.taller1.unit_test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.model.sales.Salesorderdetail;
import co.edu.icesi.model.sales.SalesorderdetailPK;
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.repositories.SalesorderdetailRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;
import co.edu.icesi.services.SalesorderdetailService;
import co.edu.icesi.services.SalesorderdetailServiceImp;

@ContextConfiguration(classes = SalesorderdetailServiceImpTest.class)
@ExtendWith(MockitoExtension.class)
class SalesorderdetailServiceImpTest {
	
	private static final int SALESORDERHEADER_ID = 6666;
	private static final int SALESORDERDETAIL_ID = 1010;
	
	private Salesorderdetail salesorderdetail;
	private Salesorderheader salesorderheader;
	private SalesorderdetailPK salesorderdetailPK;
	
	@Mock
	private SalesorderdetailRepo salesorderdetailRepoMock;
	@Mock
	private SalesorderheaderRepo salesorderheaderRepoMock;
	
	private SalesorderdetailService salesorderdetailService;
	
	
	@BeforeAll
	static void init() {
		System.out.println("\"---------------SalesorderdetailService Test-----------------\"");
	}
	
	
	@BeforeEach
	void setUp() {
		salesorderdetailService = new SalesorderdetailServiceImp(salesorderheaderRepoMock, salesorderdetailRepoMock);
		salesorderdetail = new Salesorderdetail();
		salesorderheader = new Salesorderheader();
		salesorderdetailPK = new SalesorderdetailPK();
		
		salesorderheader.addSalesorderdetail(salesorderdetail);
		salesorderdetail.setSalesorderheader(salesorderheader);
	}
	
	@Test
	void saveSalesOrderDetails() {
		
	}
	
	@Test
	void editSalesOrderDetails() {
		
	}
	
	@AfterEach
	void tearDown() {
		salesorderdetailService = null;
		salesorderdetail = null;
		salesorderheader = null;
		salesorderdetailPK = null;
		
		System.gc();
	}
	
	
	@AfterAll
	static void end() {
		System.out.println("\"---------------SalesorderdetailService Tested-----------------\"");
	}
}
