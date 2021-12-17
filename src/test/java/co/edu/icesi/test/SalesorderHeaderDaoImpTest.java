package co.edu.icesi.test;

import co.edu.icesi.TallerPruebas;
import co.edu.icesi.dao.CreditcardDaoImp;
import co.edu.icesi.dao.SalesorderheaderDaoImp;
import co.edu.icesi.dao.SalespersonDaoImp;
import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.hr.EmployeeGender;
import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.CreditcardType;
import co.edu.icesi.model.sales.Salesorderheader;
import co.edu.icesi.model.sales.Salesperson;
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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TallerPruebas.class})
@DirtiesContext
public class SalesorderHeaderDaoImpTest {

    private SalesorderheaderDaoImp salesorderheaderDaoImp;
    private Salesorderheader salesorderheader;

    private CreditcardDaoImp creditcardDaoImp;
    private Creditcard creditcard;

    private SalespersonDaoImp salespersonDaoImp;
    private Salesperson salesperson;

    private EmployeeServiceImp employeeServiceImp;
    private Employee employee;

    @Autowired
    public SalesorderHeaderDaoImpTest(SalesorderheaderDaoImp salesorderheaderDaoImp, CreditcardDaoImp creditcardDaoImp, SalespersonDaoImp salespersonDaoImp, EmployeeServiceImp employeeServiceImp) {
        this.salesorderheaderDaoImp = salesorderheaderDaoImp;
        this.creditcardDaoImp = creditcardDaoImp;
        this.salespersonDaoImp = salespersonDaoImp;
        this.employeeServiceImp = employeeServiceImp;
    }

    @BeforeAll
    public void setup1(){
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
    }

    @Test
    @Order(1)
    public void saveSalesorderheaderTest(){
        String number = "456456456";

        Salesorderheader header = new Salesorderheader();
        header.setAccountnumber(number);
        header.setOrderdate(LocalDate.parse("2030-11-07"));
        header.setShipdate(LocalDate.parse("2025-12-05"));
        header.setSubtotal(new BigDecimal(20));

        header.setCreditcard(creditcard);
        header.setSalesperson(salesperson);

        salesorderheaderDaoImp.save(header);
        salesorderheader = header;

        Optional<Salesorderheader> found = Optional.ofNullable(salesorderheaderDaoImp.findById(salesorderheader.getSalesorderid()));

        assertFalse(found.isEmpty());
        assertEquals(number, found.get().getAccountnumber());
        assertNotNull(found.get().getCreditcard());
        assertNotNull(found.get().getSalesperson());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(2)
    public void updateSalesorderheaderTest(){
        String number = "987654321";
        LocalDate orderDate = LocalDate.parse("2025-05-05");

        salesorderheader.setAccountnumber(number);
        salesorderheader.setOrderdate(orderDate);

        salesorderheaderDaoImp.update(salesorderheader);

        Optional<Salesorderheader> found = Optional.ofNullable(salesorderheaderDaoImp.findById(salesorderheader.getSalesorderid()));

        assertTrue(found.isPresent());
        assertEquals(number, found.get().getAccountnumber());
        assertEquals(orderDate, found.get().getOrderdate());
        assertNotNull(found.get().getCreditcard());
        assertNotNull(found.get().getSalesperson());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(3)
    public void deleteSalesorderheaderTest(){
        salesorderheaderDaoImp.delete(salesorderheader);
        Optional<Salesorderheader> found = Optional.ofNullable(salesorderheaderDaoImp.findById(salesorderheader.getSalesorderid()));
        assertFalse(found.isPresent());
    }
}
