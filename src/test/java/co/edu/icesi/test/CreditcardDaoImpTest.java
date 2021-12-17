package co.edu.icesi.test;

import co.edu.icesi.TallerPruebas;
import co.edu.icesi.dao.CreditcardDaoImp;
import co.edu.icesi.dao.SalesorderheaderDaoImp;
import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.model.sales.CreditcardType;
import co.edu.icesi.model.sales.Salesorderheader;
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
public class CreditcardDaoImpTest {

    private CreditcardDaoImp creditcardDaoImp;
    private Creditcard creditcard;

    private SalesorderheaderDaoImp salesorderheaderDaoImp;
    private Salesorderheader salesorderheader;

    @Autowired
    public CreditcardDaoImpTest(CreditcardDaoImp creditcardDaoImp, SalesorderheaderDaoImp salesorderheaderDaoImp){
        this.creditcardDaoImp = creditcardDaoImp;
        this.salesorderheaderDaoImp = salesorderheaderDaoImp;
    }

    @BeforeAll
    public void setup(){
        Salesorderheader header = new Salesorderheader();
        header.setAccountnumber("456456456");
        header.setOrderdate(LocalDate.parse("2030-11-07"));
        header.setShipdate(LocalDate.parse("2025-12-05"));
        header.setSubtotal(new BigDecimal(20));

        salesorderheader = header;
    }

    @Test
    @Order(1)
    public void saveCreditcardTest(){
        String number = "123456789963";

        Creditcard cc = new Creditcard();
        cc.setCardnumber("123456789963");
        cc.setCardtype(CreditcardType.PLATINUM);
        cc.setExpmonth(11);
        cc.setExpyear(2025);

        creditcardDaoImp.save(cc);
        creditcard = cc;

        try {
            salesorderheader.setCreditcard(creditcard);
            salesorderheaderDaoImp.save(salesorderheader);

        }catch (Exception e){
            assertNotNull(salesorderheader.getCreditcard(), "Credit card did not save");
        }

        Optional<Creditcard> found = Optional.ofNullable(creditcardDaoImp.findById(cc.getCreditcardid()));

        assertFalse(found.isEmpty());
        assertEquals(number, found.get().getCardnumber());
        assertEquals(CreditcardType.PLATINUM, found.get().getCardtype());
        assertEquals(11, found.get().getExpmonth());
        assertEquals(2025, found.get().getExpyear());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(2)
    public void updateCreditcardTest(){
        String number = "987654321741";

        creditcard.setCardnumber(number);
        creditcard.setCardtype(CreditcardType.VISA);

        creditcardDaoImp.update(creditcard);

        Optional<Creditcard> found = Optional.ofNullable(creditcardDaoImp.findById(creditcard.getCreditcardid()));

        assertTrue(found.isPresent());
        assertEquals(number, found.get().getCardnumber());
        assertEquals(CreditcardType.VISA, found.get().getCardtype());
        assertEquals(11, found.get().getExpmonth());
        assertEquals(2025, found.get().getExpyear());
        assertEquals(LocalDate.now(), found.get().getModifieddate());
    }

    @Test
    @Order(3)
    public void deleteCreditcardTest(){
        creditcardDaoImp.delete(creditcard);
        Optional<Creditcard> found = Optional.ofNullable(creditcardDaoImp.findById(creditcard.getCreditcardid()));
        assertFalse(found.isPresent());
    }
}
