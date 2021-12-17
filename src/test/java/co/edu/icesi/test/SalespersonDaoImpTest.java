package co.edu.icesi.test;

import co.edu.icesi.TallerPruebas;
import co.edu.icesi.dao.SalespersonDaoImp;
import co.edu.icesi.model.hr.Employee;
import co.edu.icesi.model.hr.EmployeeGender;
import co.edu.icesi.model.sales.Creditcard;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TallerPruebas.class})
@DirtiesContext
public class SalespersonDaoImpTest {

    private SalespersonDaoImp salespersonDaoImp;
    private Salesperson salesperson;

    private EmployeeServiceImp employeeServiceImp;
    private Employee employee;

    @Autowired
    public SalespersonDaoImpTest(SalespersonDaoImp salespersonDaoImp, EmployeeServiceImp employeeServiceImp){
        this.salespersonDaoImp = salespersonDaoImp;
        this.employeeServiceImp = employeeServiceImp;
    }

    @BeforeAll
    public void setup(){
        Employee e = new Employee();
        e.setBirthdate(LocalDate.parse("2000-05-18"));
        e.setCurrentflag("Activo");
        e.setGender(EmployeeGender.MALE);
        e.setHiredate(LocalDate.parse("2021-01-10"));
        e.setJobtitle("Arquitecto");
        e.setMaritalstatus("Soltero como yo");

        employeeServiceImp.saveEmployee(e);
        employee = e;
    }

    @Test
    @Order(1)
    public void saveSalesperson(){
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

        Optional<Salesperson> found = Optional.ofNullable(salespersonDaoImp.findById(salesperson.getBusinessentityid()));

        assertFalse(found.isEmpty());
        assertEquals(1, found.get().getBusinessentityid());
        assertNotNull(found.get().getEmployee());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(2)
    public void updateSalesperson(){
        BigDecimal salesytd = new BigDecimal("100.00");
        BigDecimal bonus = new BigDecimal("50.00");

        salesperson.setSalesytd(salesytd);
        salesperson.setBonus(bonus);

        salespersonDaoImp.update(salesperson);

        Optional<Salesperson> found = Optional.ofNullable(salespersonDaoImp.findById(salesperson.getBusinessentityid()));

        assertTrue(found.isPresent());
        assertEquals(1, found.get().getBusinessentityid());
        assertEquals(salesytd, found.get().getSalesytd());
        assertEquals(bonus, found.get().getBonus());
        assertNotNull(found.get().getEmployee());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(3)
    public void deleteSalesperson(){
        salespersonDaoImp.delete(salesperson);
        Optional<Salesperson> found = Optional.ofNullable(salespersonDaoImp.findById(salesperson.getBusinessentityid()));
        assertFalse(found.isPresent());
    }
}
