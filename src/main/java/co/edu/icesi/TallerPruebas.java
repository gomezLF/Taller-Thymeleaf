package co.edu.icesi;


import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.hr.EmployeeGender;
import co.edu.icesi.model.person.Businessentity;
import co.edu.icesi.model.person.Person;
import co.edu.icesi.model.person.User;
import co.edu.icesi.model.person.UserType;
import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.CreditcardType;
import co.edu.icesi.model.sales.Salesorderdetail;
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.model.sales.Salesperson;
import co.edu.icesi.repositories.BusinessentityRepo;
import co.edu.icesi.repositories.PersonRepo;
import co.edu.icesi.services.CreditcardServiceImp;
import co.edu.icesi.services.EmployeeServiceImp;
import co.edu.icesi.services.SalesorderdetailServiceImp;
import co.edu.icesi.services.SalesorderheaderServiceImp;
import co.edu.icesi.services.SalespersonServiceImp;
import co.edu.icesi.services.UserServiceImp;

@SpringBootApplication
@ComponentScan(basePackages = {"co.edu.icesi"})
public class TallerPruebas {
	
	private static Creditcard creditcard;
	private static Employee employee;
	private static Salesperson salesperson;
	private static Salesorderdetail salesorderdetail;
	private static Salesorderheader salesorderheader;
	
	private static UserServiceImp userService;
	private static CreditcardServiceImp creditcardServiceImp;
	private static SalesorderdetailServiceImp salesorderdetailServiceImp;
	private static SalesorderheaderServiceImp salesorderheaderServiceImp;
	private static SalespersonServiceImp salespersonServiceImp;
	private static EmployeeServiceImp employeeServiceImp;
	
	private static BusinessentityRepo businessentityRepo;
	private static PersonRepo personRepository;
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TallerPruebas.class, args);
		
		userService = context.getBean(UserServiceImp.class);
		creditcardServiceImp = context.getBean(CreditcardServiceImp.class);
		salesorderdetailServiceImp = context.getBean(SalesorderdetailServiceImp.class);
		salesorderheaderServiceImp = context.getBean(SalesorderheaderServiceImp.class);
		salespersonServiceImp = context.getBean(SalespersonServiceImp.class);
		employeeServiceImp = context.getBean(EmployeeServiceImp.class);

		businessentityRepo = context.getBean(BusinessentityRepo.class);
		personRepository = context.getBean(PersonRepo.class);
		
		firstStep(context);
		addUsers(context);
		addCreditcards(context);
		addEmployee(context);
		addSalespersons(context);
		addSalesorderdetails(context);
		addSalesheaders(context);
		finalStep(context);
	}
	
	private static void firstStep(ConfigurableApplicationContext context) {
		Businessentity businessentity = new Businessentity();
		
		businessentityRepo.save(businessentity);
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
		Creditcard cc = new Creditcard();
		cc.setCardnumber("123456789963");
		cc.setCardtype(CreditcardType.PLATINUM);
		cc.setExpmonth(11);
		cc.setExpyear(2025);
		
		creditcard = cc; 
	}
	
	private static void addEmployee(ConfigurableApplicationContext context) {
		Employee e = new Employee();
		e.setBirthdate(LocalDate.parse("1999-05-18"));
		e.setCurrentflag("Activo");
		e.setGender(EmployeeGender.MALE);
		e.setHiredate(LocalDate.parse("2021-01-10"));
		e.setJobtitle("Ingeniero");
		e.setMaritalstatus("Casado");
		
		employee = e;
	}
	
	private static void addSalespersons(ConfigurableApplicationContext context) {
		Salesperson  s = new Salesperson();
		
		s.setSalesquota(new BigDecimal(156));
		s.setBonus(new BigDecimal(25));
		s.setCommissionpct(new BigDecimal(58));
		s.setRowguid(15);
		s.setSaleslastyear(new BigDecimal(25));
		s.setSalesytd(new BigDecimal(12));
		
		salesperson = s;
	}
	
	private static void addSalesheaders(ConfigurableApplicationContext context) {
		Salesorderheader header = new Salesorderheader();
		header.setAccountnumber("456456456");
		header.setOrderdate(LocalDate.parse("2030-11-07"));
		header.setShipdate(LocalDate.parse("2025-12-05"));
		header.setSubtotal(new BigDecimal(20));
		
		salesorderheader = header;
	}
	
	private static void addSalesorderdetails(ConfigurableApplicationContext context) {
		Salesorderdetail s = new Salesorderdetail();
		
		s.setOrderqty(10);
		s.setCarriertrackingnumber("100");
		s.setRowguid(10);
		s.setUnitprice(new BigDecimal(5));
		s.setUnitpricediscount(new BigDecimal(7));
		
		salesorderdetail = s;
	}

	
	private static void finalStep(ConfigurableApplicationContext context) {
		creditcardServiceImp.saveCreditCard(creditcard);

		employeeServiceImp.saveEmployee(employee);

		salesperson.setEmployee(employee);
		salespersonServiceImp.saveSalesPerson(salesperson);
		
		salesorderheader.setCreditcard(creditcard);
		salesorderheader.setSalesperson(salesperson);
		salesorderheaderServiceImp.saveSalesOrderHeader(salesorderheader);
		
		salesorderdetail.setSalesorderheader(salesorderheader);
		salesorderdetailServiceImp.saveSalesOrderDetails(salesorderdetail);
	}
	
}
