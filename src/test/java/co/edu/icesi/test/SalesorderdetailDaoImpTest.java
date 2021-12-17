package co.edu.icesi.test;

import co.edu.icesi.TallerPruebas;
import co.edu.icesi.dao.CreditcardDaoImp;
import co.edu.icesi.dao.SalesorderdetailDaoImp;
import co.edu.icesi.dao.SalesorderheaderDaoImp;
import co.edu.icesi.dao.SalespersonDaoImp;
import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.hr.EmployeeGender;
import co.edu.icesi.model.sales.*;
import co.edu.icesi.services.EmployeeServiceImp;
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
@ContextConfiguration(classes = {TallerPruebas.class})
@DirtiesContext
public class SalesorderdetailDaoImpTest {

    private SalesorderdetailDaoImp salesorderdetailDaoImp;
    private Salesorderdetail salesorderdetail;

    private SalesorderheaderDaoImp salesorderheaderDaoImp;
    private Salesorderheader salesorderheader;

    private CreditcardDaoImp creditcardDaoImp;
    private Creditcard creditcard;

    private SalespersonDaoImp salespersonDaoImp;
    private Salesperson salesperson;

    private EmployeeServiceImp employeeServiceImp;
    private Employee employee;

    @Autowired
    public SalesorderdetailDaoImpTest(SalesorderdetailDaoImp salesorderdetailDaoImp, SalesorderheaderDaoImp salesorderheaderDaoImp, CreditcardDaoImp creditcardDaoImp, SalespersonDaoImp salespersonDaoImp, EmployeeServiceImp employeeServiceImp){
        this.salesorderdetailDaoImp = salesorderdetailDaoImp;
        this.salesorderheaderDaoImp = salesorderheaderDaoImp;
        this.creditcardDaoImp = creditcardDaoImp;
        this.salespersonDaoImp = salespersonDaoImp;
        this.employeeServiceImp = employeeServiceImp;
    }

    @BeforeAll
    public void setup(){
        Creditcard cc = new Creditcard();
        cc.setCardnumber("123456789963");
        cc.setCardtype(CreditcardType.PLATINUM);
        cc.setExpmonth(11);
        cc.setExpyear(2025);

        creditcardDaoImp.save(cc);
        creditcard = cc;

        setup2();
    }

    private void setup2(){
        Employee e = new Employee();
        e.setBirthdate(LocalDate.parse("1999-05-18"));
        e.setCurrentflag("Activo");
        e.setGender(EmployeeGender.MALE);
        e.setHiredate(LocalDate.parse("2021-01-10"));
        e.setJobtitle("Ingeniero");
        e.setMaritalstatus("Casado");

        employeeServiceImp.saveEmployee(e);
        employee = e;

        setup3();
    }

    private void setup3(){
        Salesperson  s = new Salesperson();
        s.setSalesquota(new BigDecimal(156));
        s.setBonus(new BigDecimal(25));
        s.setCommissionpct(new BigDecimal(58));
        s.setRowguid(15);
        s.setSaleslastyear(new BigDecimal(25));
        s.setSalesytd(new BigDecimal(12));
        s.setEmployee(employee);

        salespersonDaoImp.save(s);
        salesperson = s;

        setup4();
    }

    private void setup4(){
        Salesorderheader header = new Salesorderheader();
        header.setAccountnumber("456456456");
        header.setOrderdate(LocalDate.parse("2030-11-07"));
        header.setShipdate(LocalDate.parse("2025-12-05"));
        header.setSubtotal(new BigDecimal(20));

        header.setCreditcard(creditcard);
        header.setSalesperson(salesperson);

        salesorderheaderDaoImp.save(header);
        salesorderheader = header;
    }

    @Test
    @Order(1)
    public void saveSalesorderdetailTest(){
        Salesorderdetail s = new Salesorderdetail();
        s.setOrderqty(10);
        s.setCarriertrackingnumber("100");
        s.setRowguid(10);
        s.setUnitprice(new BigDecimal(5));
        s.setUnitpricediscount(new BigDecimal(7));
        s.setSalesorderheader(salesorderheader);

        salesorderdetailDaoImp.save(s);
        salesorderdetail = s;

        Optional<Salesorderdetail> found = Optional.ofNullable(salesorderdetailDaoImp.findById(salesorderdetail.getId()));

        assertFalse(found.isEmpty());
        assertEquals(1, found.get().getId());
        assertNotNull(found.get().getSalesorderheader());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(2)
    public void updateSalesorderdetailTest(){
        salesorderdetail.setCarriertrackingnumber("500");
        salesorderdetail.setOrderqty(80);

        salesorderdetailDaoImp.update(salesorderdetail);

        Optional<Salesorderdetail> found = Optional.ofNullable(salesorderdetailDaoImp.findById(salesorderdetail.getId()));

        assertTrue(found.isPresent());
        assertEquals(1, found.get().getId());
        assertEquals("500", found.get().getCarriertrackingnumber());
        assertEquals(80, found.get().getOrderqty());
        assertNotNull(found.get().getSalesorderheader());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(3)
    public void deleteSalesorderdetailTest(){
        salesorderdetailDaoImp.delete(salesorderdetail);
        Optional<Salesorderdetail> found = Optional.ofNullable(salesorderdetailDaoImp.findById(salesorderdetail.getId()));
        assertFalse(found.isPresent());
    }
}
