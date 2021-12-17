package co.edu.icesi.test;

import co.edu.icesi.TallerPruebas;
import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.hr.EmployeeGender;
import co.edu.icesi.model.sales.*;
import co.edu.icesi.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TallerPruebas.class)
@DirtiesContext
public class TallerPruebasIntegrationTest {

    private CreditcardServiceImp creditcardServiceImp;
    private Creditcard creditcardId;

    private EmployeeServiceImp employeeServiceImp;
    private Employee employeeId;

    private SalespersonServiceImp salespersonServiceImp;
    private Salesperson salespersonId;

    private SalesorderheaderServiceImp salesorderheaderServiceImp;
    private Salesorderheader salesorderheaderId;

    private SalesorderdetailServiceImp salesorderdetailServiceImp;
    private Salesorderdetail salesorderdetailId;

    @Autowired
    public TallerPruebasIntegrationTest(CreditcardServiceImp creditcardServiceImp, EmployeeServiceImp employeeServiceImp, SalespersonServiceImp salespersonServiceImp, SalesorderheaderServiceImp salesorderheaderServiceImp, SalesorderdetailServiceImp salesorderdetailServiceImp){
        this.creditcardServiceImp = creditcardServiceImp;
        this.employeeServiceImp = employeeServiceImp;
        this.salespersonServiceImp = salespersonServiceImp;
        this.salesorderheaderServiceImp = salesorderheaderServiceImp;
        this.salesorderdetailServiceImp = salesorderdetailServiceImp;
    }

    @BeforeAll
    public void setup(){
        Employee e = new Employee();
        e.setBirthdate(LocalDate.parse("1999-05-18"));
        e.setCurrentflag("Activo");
        e.setGender(EmployeeGender.MALE);
        e.setHiredate(LocalDate.parse("2021-01-10"));
        e.setJobtitle("Ingeniero");
        e.setMaritalstatus("Casado");

        employeeServiceImp.saveEmployee(e);
        employeeId = e;
    }

    @Test
    @Order(1)
    public void saveCreditcardTest(){
        String number = "123456789963";

        Creditcard cc = new Creditcard();
        cc.setCardnumber(number);
        cc.setCardtype(CreditcardType.PLATINUM);
        cc.setExpmonth(11);
        cc.setExpyear(2025);

        creditcardServiceImp.saveCreditCard(cc);
        creditcardId = cc;

        Optional<Creditcard> found = creditcardServiceImp.findCreditCard(creditcardId.getCreditcardid());

        assertFalse(found.isEmpty());
        assertEquals(number, found.get().getCardnumber());
        assertEquals(CreditcardType.PLATINUM, found.get().getCardtype());
        assertEquals(11, found.get().getExpmonth());
        assertEquals(2025, found.get().getExpyear());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(2)
    public void editCreditcardTest(){
        String number = "987654321741";

        creditcardId.setCardnumber(number);
        creditcardId.setCardtype(CreditcardType.VISA);

        creditcardServiceImp.editCreditCard(creditcardId);

        Optional<Creditcard> found = creditcardServiceImp.findCreditCard(creditcardId.getCreditcardid());

        assertTrue(found.isPresent());
        assertEquals(number, found.get().getCardnumber());
        assertEquals(CreditcardType.VISA, found.get().getCardtype());
        assertEquals(11, found.get().getExpmonth());
        assertEquals(2025, found.get().getExpyear());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(3)
    public void addSalespersonTest(){
        Salesperson  s = new Salesperson();
        s.setSalesquota(new BigDecimal(156));
        s.setBonus(new BigDecimal(25));
        s.setCommissionpct(new BigDecimal(58));
        s.setRowguid(15);
        s.setSaleslastyear(new BigDecimal(25));
        s.setSalesytd(new BigDecimal(12));
        s.setEmployee(employeeId);

        salespersonServiceImp.saveSalesPerson(s);
        salespersonId = s;

        Optional<Salesperson> found = salespersonServiceImp.findSalesperson(salespersonId.getBusinessentityid());

        assertFalse(found.isEmpty());
        assertEquals(1, found.get().getBusinessentityid());
        assertNotNull(found.get().getEmployee());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(4)
    public void editSalespersonTest(){
        BigDecimal salesytd = new BigDecimal("500.00");
        BigDecimal bonus = new BigDecimal("10.00");

        salespersonId.setSalesytd(salesytd);
        salespersonId.setBonus(bonus);

        salespersonServiceImp.editSalesPerson(salespersonId);

        Optional<Salesperson> found = salespersonServiceImp.findSalesperson(salespersonId.getBusinessentityid());

        assertTrue(found.isPresent());
        assertEquals(1, found.get().getBusinessentityid());
        assertEquals(salesytd, found.get().getSalesytd());
        assertEquals(bonus, found.get().getBonus());
        assertNotNull(found.get().getEmployee());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(5)
    public void addSalesorderheaderTest(){
        String number = "456456456";

        Salesorderheader header = new Salesorderheader();
        header.setAccountnumber(number);
        header.setOrderdate(LocalDate.parse("2030-11-07"));
        header.setShipdate(LocalDate.parse("2025-12-05"));
        header.setSubtotal(new BigDecimal(20));

        header.setCreditcard(creditcardId);
        header.setSalesperson(salespersonId);

        salesorderheaderServiceImp.saveSalesOrderHeader(header);
        salesorderheaderId = header;

        Optional<Salesorderheader> found = salesorderheaderServiceImp.findSalesorderheader(salesorderheaderId.getSalesorderid());

        assertFalse(found.isEmpty());
        assertEquals(number, found.get().getAccountnumber());
        assertNotNull(found.get().getCreditcard());
        assertNotNull(found.get().getSalesperson());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(6)
    public void editSalesorderheaderTest(){
        String number = "987654321";
        LocalDate orderDate = LocalDate.parse("2035-11-08");

        salesorderheaderId.setAccountnumber(number);
        salesorderheaderId.setOrderdate(orderDate);

        salesorderheaderServiceImp.editSalesOrderHeader(salesorderheaderId);

        Optional<Salesorderheader> found = salesorderheaderServiceImp.findSalesorderheader(salesorderheaderId.getSalesorderid());

        assertTrue(found.isPresent());
        assertEquals(number, found.get().getAccountnumber());
        assertEquals(orderDate, found.get().getOrderdate());
        assertNotNull(found.get().getCreditcard());
        assertNotNull(found.get().getSalesperson());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(7)
    public void addSalesorderdetailTest(){
        Salesorderdetail s = new Salesorderdetail();
        s.setOrderqty(10);
        s.setCarriertrackingnumber("100");
        s.setRowguid(10);
        s.setUnitprice(new BigDecimal(5));
        s.setUnitpricediscount(new BigDecimal(7));

        s.setSalesorderheader(salesorderheaderId);

        salesorderdetailServiceImp.saveSalesOrderDetails(s);
        salesorderdetailId = s;

        Optional<Salesorderdetail> found = salesorderdetailServiceImp.findSalesorderdetail(salesorderdetailId.getId());

        assertFalse(found.isEmpty());
        assertEquals(1, found.get().getId());
        assertNotNull(found.get().getSalesorderheader());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(8)
    public void editSalesorderdetailTest(){
        salesorderdetailId.setCarriertrackingnumber("500");
        salesorderdetailId.setOrderqty(80);

        salesorderdetailServiceImp.editSalesOrderDetails(salesorderdetailId);

        Optional<Salesorderdetail> found = salesorderdetailServiceImp.findSalesorderdetail(salesorderdetailId.getId());

        assertTrue(found.isPresent());
        assertEquals(1, found.get().getId());
        assertEquals("500", found.get().getCarriertrackingnumber());
        assertEquals(80, found.get().getOrderqty());
        assertNotNull(found.get().getSalesorderheader());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }
}
