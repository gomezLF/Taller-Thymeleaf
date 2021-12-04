package co.edu.icesi.model.sales;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQuery(name="Creditcard.findAll", query="SELECT c FROM Creditcard c")
public class Creditcard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CREDITCARD_CREDITCARDID_GENERATOR", sequenceName="CREDITCARD_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CREDITCARD_CREDITCARDID_GENERATOR")
	@Column(name="CREDITCARD_ID")
	private Integer creditcardid;
	
	@Column(name = "CARD_NUMBER")
	@NotBlank(message = "El numero de tarjeta no debe estar vacio")
	@Size(min = 12, max = 12, message = "El numero de tarjeta debe contener 12 caracteres")
	private String cardnumber;
	
	@Column(name = "CARD_TYPE")
	@NotNull(message = "El tipo de tarjeta no debe ser null")
	private CreditcardType cardtype;
	
	@NotNull( message = "El mes de vencimiento no debe estar vacio.")
	private Integer expmonth;
	
	@NotNull(message = "El año de vencimiento no debe estar vacio.")
	@Min(value = 2022, message = "El año debe ser mayor al actual")
	private Integer expyear;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "La fecha de modificación debe ser la actual")
	private LocalDate modifieddate;

	//bi-directional many-to-one association to Personcreditcard
	@OneToMany(mappedBy="creditcard")
	private List<Personcreditcard> personcreditcards;

	//bi-directional many-to-one association to Salesorderheader
	@OneToMany(mappedBy="creditcard")
	private List<Salesorderheader> salesorderheaders;

	public Creditcard() {
		setSalesorderheaders(new ArrayList<>());
		setModifieddate(LocalDate.now());
		//salesorderheaders.iterator()
	}

	public Integer getCreditcardid() {
		return this.creditcardid;
	}

	public void setCreditcardid(Integer creditcardid) {
		this.creditcardid = creditcardid;
	}

	public String getCardnumber() {
		return this.cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public CreditcardType getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(CreditcardType cardtype) {
		this.cardtype = cardtype;
	}

	public Integer getExpmonth() {
		return this.expmonth;
	}

	public void setExpmonth(Integer expmonth) {
		this.expmonth = expmonth;
	}

	public Integer getExpyear() {
		return this.expyear;
	}

	public void setExpyear(Integer expyear) {
		this.expyear = expyear;
	}

	public LocalDate getModifieddate() {
		return this.modifieddate;
	}

	public void setModifieddate(LocalDate modifieddate) {
		this.modifieddate = modifieddate;
	}

	public List<Personcreditcard> getPersoncreditcards() {
		return this.personcreditcards;
	}

	public void setPersoncreditcards(List<Personcreditcard> personcreditcards) {
		this.personcreditcards = personcreditcards;
	}

	public Personcreditcard addPersoncreditcard(Personcreditcard personcreditcard) {
		getPersoncreditcards().add(personcreditcard);
		personcreditcard.setCreditcard(this);

		return personcreditcard;
	}

	public Personcreditcard removePersoncreditcard(Personcreditcard personcreditcard) {
		getPersoncreditcards().remove(personcreditcard);
		personcreditcard.setCreditcard(null);

		return personcreditcard;
	}

	public List<Salesorderheader> getSalesorderheaders() {
		return this.salesorderheaders;
	}

	public void setSalesorderheaders(List<Salesorderheader> salesorderheaders) {
		this.salesorderheaders = salesorderheaders;
	}

	public Salesorderheader addSalesorderheader(Salesorderheader salesorderheader) {
		getSalesorderheaders().add(salesorderheader);
		salesorderheader.setCreditcard(this);

		return salesorderheader;
	}

	public Salesorderheader removeSalesorderheader(Salesorderheader salesorderheader) {
		getSalesorderheaders().remove(salesorderheader);
		salesorderheader.setCreditcard(null);

		return salesorderheader;
	}

}