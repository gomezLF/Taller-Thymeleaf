package co.edu.icesi;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import co.edu.icesi.model.person.Businessentity;
import co.edu.icesi.model.person.Person;
import co.edu.icesi.model.person.User;
import co.edu.icesi.model.person.UserType;
import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.CreditcardType;
import co.edu.icesi.model.sales.Salesorderdetail;
import co.edu.icesi.model.sales.SalesorderdetailPK;
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.BusinessentityRepo;
import co.edu.icesi.repositories.CreditcardRepo;
import co.edu.icesi.repositories.EmployeeRepo;
import co.edu.icesi.repositories.PersonRepo;
import co.edu.icesi.repositories.SalesorderdetailRepo;
import co.edu.icesi.repositories.SalesorderheaderRepo;
import co.edu.icesi.repositories.SalespersonRepo;
import co.edu.icesi.services.CreditcardServiceImp;
import co.edu.icesi.services.SalesorderdetailServiceImp;
import co.edu.icesi.services.SalesorderheaderServiceImp;
import co.edu.icesi.services.SalespersonServiceImp;
import co.edu.icesi.services.UserServiceImp;

@SpringBootApplication
@ComponentScan(basePackages = {"co.edu.icesi"})
public class TallerPruebas {
	
	
	private static int headerId;
	private static int creditcardId;
	private static int salespersonId;
	private static int businessentityId;
	
	private static UserServiceImp userService;
	private static CreditcardServiceImp creditcardServiceImp;
	private static SalesorderdetailServiceImp salesorderdetailServiceImp;
	private static SalesorderheaderServiceImp salesorderheaderServiceImp;
	private static SalespersonServiceImp salespersonServiceImp;
	
	
	private static CreditcardRepo creditcardRepo;
	private static SalesorderdetailRepo salesorderdetailRepo;
	private static SalesorderheaderRepo salesorderheaderRepo;
	private static SalespersonRepo salespersonRepo;
	private static BusinessentityRepo businessentityRepo;
	private static EmployeeRepo employeeRepo;
	private static PersonRepo personRepository;
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TallerPruebas.class, args);
		
		userService = context.getBean(UserServiceImp.class);
		creditcardServiceImp = context.getBean(CreditcardServiceImp.class);
		salesorderdetailServiceImp = context.getBean(SalesorderdetailServiceImp.class);
		salesorderheaderServiceImp = context.getBean(SalesorderheaderServiceImp.class);
		salespersonServiceImp = context.getBean(SalespersonServiceImp.class);
		
		creditcardRepo = context.getBean(CreditcardRepo.class);
		salesorderdetailRepo = context.getBean(SalesorderdetailRepo.class);
		salesorderheaderRepo = context.getBean(SalesorderheaderRepo.class);
		salespersonRepo = context.getBean(SalespersonRepo.class);
		businessentityRepo = context.getBean(BusinessentityRepo.class);
		employeeRepo = context.getBean(EmployeeRepo.class);
		personRepository = context.getBean(PersonRepo.class);
		
		firstStep(context);
		addUsers(context);
		addCreditcards(context);
		addSalespersons(context);
		addSalesheaders(context);
		addSalesorderdetails(context);
		finalStep(context);
	}
	
	private static void firstStep(ConfigurableApplicationContext context) {
		Businessentity businessentity = new Businessentity();
		
		businessentityRepo.save(businessentity);
		businessentityId = businessentity.getBusinessentityid();
	}
	
	private static void addUsers(ConfigurableApplicationContext context) {
		User user = new User();
		Person person = new Person();
		personRepository.save(person);
		user.setPerson(person);
		user.setUserName("admin");
		user.setUserPassword("{noop}valle");
		user.setUserType(UserType.ADMINISTRATIOR);
		userService.save(user);
		
		User user2 = new User();
		Person person2 = new Person();
		personRepository.save(person2);
		user2.setUserId(11111);
		user2.setUserName("operador");
		user2.setUserPassword("{noop}icesi123");
		user2.setUserType(UserType.OPERATOR);
		userService.save(user2);
	}
	
	private static void addCreditcards(ConfigurableApplicationContext context) {
		Creditcard creditcard = new Creditcard();
		//creditcard.setCreditcardid(456);
		creditcard.setCardnumber("123456789963");
		creditcard.setCardtype(CreditcardType.PLATINUM);
		creditcard.setExpmonth(11);
		creditcard.setExpyear(2025);
		
		creditcardServiceImp.saveCreditCard(creditcard);
		creditcardId = creditcard.getCreditcardid();
		
	}
	
	private static void addSalespersons(ConfigurableApplicationContext context) {
		Salesperson  s = new Salesperson();
		
		s.setSalesquota(new BigDecimal(156));
		s.setBonus(new BigDecimal(25));
		s.setCommissionpct(new BigDecimal(58));
		s.setRowguid(15);
		s.setSaleslastyear(new BigDecimal(25));
		s.setSalesytd(new BigDecimal(12));
		
		salespersonServiceImp.saveSalesPerson(s);
		
		Salesperson s2 = s;
		s2.setBusinessentityid(businessentityId);
		
		salespersonServiceImp.editSalesPerson(s2);
		salespersonId = s2.getBusinessentityid();
	}
	
	private static void addSalesheaders(ConfigurableApplicationContext context) {
		Salesorderheader header = new Salesorderheader();
		header.setAccountnumber("456456456");
		header.setOrderdate(LocalDate.parse("2030-11-07"));
		header.setShipdate(LocalDate.parse("2025-12-05"));
		header.setSubtotal(new BigDecimal(20));
		
		
		header.setCreditcard(creditcardServiceImp.findCreditCard(creditcardId).get());
		header.setSalesperson(salespersonServiceImp.findSalesperson(salespersonId).get());
		
		salesorderheaderServiceImp.saveSalesOrderHeader(header);
		headerId = header.getSalesorderid();
	}
	
	private static void addSalesorderdetails(ConfigurableApplicationContext context) {
		Salesorderdetail s = new Salesorderdetail();
		
		s.setOrderqty(10);
		s.setCarriertrackingnumber("100");
		s.setRowguid(10);
		s.setUnitprice(new BigDecimal(5));
		s.setUnitpricediscount(new BigDecimal(7));
		s.setSalesorderheader(salesorderheaderServiceImp.findSalesorderheader(headerId).get());
		//sod1 = sod.save(s).getId();
		
		
		salesorderdetailServiceImp.saveSalesOrderDetails(s);
		System.out.println(s.getId());
	}
	
	private static void finalStep(ConfigurableApplicationContext context) {
		
	}
	
}
