package com.taller1.unit_test;


import static org.mockito.Mockito.when;

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
import co.edu.icesi.model.sales.CreditcardType;
import co.edu.icesi.repositories.CreditcardRepo;
import co.edu.icesi.services.CreditcardService;
import co.edu.icesi.services.CreditcardServiceImp;

@ContextConfiguration(classes = CreditcardServiceImpTest.class)
@ExtendWith(MockitoExtension.class)
class CreditcardServiceImpTest {
	
	private static final int CREDITCARD_ID = 2020;
	
	private Creditcard creditcard;
	
	@Mock
	private CreditcardRepo creditcardRepoMock;
	
	private CreditcardService creditcardService;
	
	
	@BeforeAll
	static void init() {
		System.out.println("\"---------------CreditcardService Test-----------------\"");
	}
	
	
	@BeforeEach
	void setUp() {
		creditcardService = new CreditcardServiceImp(creditcardRepoMock);
		creditcard = new Creditcard();
		
		creditcard.setCreditcardid(CREDITCARD_ID);
		
		creditcard.setCardnumber("123456789123");
		creditcard.setCardtype(CreditcardType.MASTER_CARD);
		creditcard.setExpyear(2025);
		creditcard.setExpmonth(11);
	}
	
	@Test
	void saveCreditCardTest() {
		when(creditcardRepoMock.findById((CREDITCARD_ID))).thenReturn(Optional.of(creditcard));
		
		try {
			creditcardService.saveCreditCard(creditcard);
		} catch (LogicalException e) {
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(creditcardService.findCreditCard(CREDITCARD_ID).get().getCreditcardid(), "El objeto Creditcard no esta agregado al repositorio");
	
	}
	
	@Test
	void editCreditCardTest() {
		when(creditcardRepoMock.findById((CREDITCARD_ID))).thenReturn(Optional.of(creditcard));
		
		try {
			creditcardService.editCreditCard(creditcard);
		} catch (LogicalException e) {
			e.printStackTrace();
		}
		
		Assertions.assertNotNull(creditcardService.findCreditCard(CREDITCARD_ID).get().getCreditcardid(), "El objeto Creditcard no esta agregado al repositorio");
	
	}
	
	@AfterEach
	void tearDown() {
		creditcard = null;
		creditcardService = null;
		
		System.gc();
	}
	
	
	@AfterAll
	static void end() {
		System.out.println("\"---------------CreditcardService Tested-----------------\"");
	}
}
