package co.edu.icesi.model.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the salesorderheader database table.
 *
 */
@Entity
@NamedQuery(name = "Salesorderheader.findAll", query = "SELECT s FROM Salesorderheader s")
public class Salesorderheader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SALESORDERHEADER_SALESORDERID_GENERATOR", allocationSize = 1, sequenceName = "SALESORDERHEADER_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALESORDERHEADER_SALESORDERID_GENERATOR")
	private Integer salesorderid;
	
	@NotBlank(message = "El campo no debe estar vacío")
	private String accountnumber;

	private Integer billtoaddressid;

	private String comment;

	private String creditcardapprovalcode;
	
	private LocalDate duedate;

	private BigDecimal freight;
	
	@NotNull(message = "El campo no debe estar vacío")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "La fecha debe ser la fecha actual")
	private LocalDate modifieddate;

	private String onlineorderflag;
	
	@NotNull(message = "El campo no debe estar vacío")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "La fecha debe ser la fecha actual")
	private LocalDate orderdate;

	private String purchaseordernumber;

	private Integer revisionnumber;

	private Integer rowguid;
	
	@NotNull(message = "El campo no debe estar vacío")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future(message = "La fecha debe ser posterior a la fecha actual")
	private LocalDate shipdate;

	private Integer shipmethodid;

	private Integer shiptoaddressid;

	private Integer status;
	
	@NotNull(message = "El campo no debe estar vacío")
	@Min(value = 1, message = "El campo debe tener un valor mayor a cero")
	private BigDecimal subtotal;

	private BigDecimal taxamt;

	private BigDecimal totaldue;

	// bi-directional many-to-one association to Salesorderdetail
	@OneToMany(mappedBy = "salesorderheader")
	private List<Salesorderdetail> salesorderdetails;

	// bi-directional many-to-one association to Creditcard
	@ManyToOne
	@JoinColumn(name = "creditcardid")
	//@NotNull(message = "Debe escoger una tarjeta de credito")
	private Creditcard creditcard;

	// bi-directional many-to-one association to Currencyrate
	@ManyToOne
	@JoinColumn(name = "currencyrateid")
	private Currencyrate currencyrate;

	// bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name = "customerid")
	private Customer customer;

	// bi-directional many-to-one association to Salesperson
	@ManyToOne
	@JoinColumn(name = "salespersonid")
	@NotNull(message = "Debe escoger un vendedor")
	private Salesperson salesperson;

	// bi-directional many-to-one association to Salesterritory
	@ManyToOne
	@JoinColumn(name = "territoryid")
	private Salesterritory salesterritory;

	// bi-directional many-to-one association to Salesorderheadersalesreason
	@OneToMany(mappedBy = "salesorderheader")
	private List<Salesorderheadersalesreason> salesorderheadersalesreasons;

	public Salesorderheader() {
		setSalesorderdetails(new ArrayList<Salesorderdetail>());
		setModifieddate(LocalDate.now());
	}

	public Salesorderdetail addSalesorderdetail(Salesorderdetail salesorderdetail) {
		getSalesorderdetails().add(salesorderdetail);
		salesorderdetail.setSalesorderheader(this);

		return salesorderdetail;
	}

	public Salesorderheadersalesreason addSalesorderheadersalesreason(
			Salesorderheadersalesreason salesorderheadersalesreason) {
		getSalesorderheadersalesreasons().add(salesorderheadersalesreason);
		salesorderheadersalesreason.setSalesorderheader(this);

		return salesorderheadersalesreason;
	}

	public String getAccountnumber() {
		return this.accountnumber;
	}

	public Integer getBilltoaddressid() {
		return this.billtoaddressid;
	}

	public String getComment() {
		return this.comment;
	}

	public Creditcard getCreditcard() {
		return this.creditcard;
	}

	public String getCreditcardapprovalcode() {
		return this.creditcardapprovalcode;
	}

	public Currencyrate getCurrencyrate() {
		return this.currencyrate;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public LocalDate getDuedate() {
		return this.duedate;
	}

	public BigDecimal getFreight() {
		return this.freight;
	}

	public LocalDate getModifieddate() {
		return this.modifieddate;
	}

	public String getOnlineorderflag() {
		return this.onlineorderflag;
	}

	public LocalDate getOrderdate() {
		return this.orderdate;
	}

	public String getPurchaseordernumber() {
		return this.purchaseordernumber;
	}

	public Integer getRevisionnumber() {
		return this.revisionnumber;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public List<Salesorderdetail> getSalesorderdetails() {
		return this.salesorderdetails;
	}

	public List<Salesorderheadersalesreason> getSalesorderheadersalesreasons() {
		return this.salesorderheadersalesreasons;
	}

	public Integer getSalesorderid() {
		return this.salesorderid;
	}

	public Salesperson getSalesperson() {
		return this.salesperson;
	}

	public Salesterritory getSalesterritory() {
		return this.salesterritory;
	}

	public LocalDate getShipdate() {
		return this.shipdate;
	}

	public Integer getShipmethodid() {
		return this.shipmethodid;
	}

	public Integer getShiptoaddressid() {
		return this.shiptoaddressid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public BigDecimal getTaxamt() {
		return this.taxamt;
	}

	public BigDecimal getTotaldue() {
		return this.totaldue;
	}

	public Salesorderdetail removeSalesorderdetail(Salesorderdetail salesorderdetail) {
		getSalesorderdetails().remove(salesorderdetail);
		salesorderdetail.setSalesorderheader(null);

		return salesorderdetail;
	}

	public Salesorderheadersalesreason removeSalesorderheadersalesreason(
			Salesorderheadersalesreason salesorderheadersalesreason) {
		getSalesorderheadersalesreasons().remove(salesorderheadersalesreason);
		salesorderheadersalesreason.setSalesorderheader(null);

		return salesorderheadersalesreason;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public void setBilltoaddressid(Integer billtoaddressid) {
		this.billtoaddressid = billtoaddressid;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreditcard(Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public void setCreditcardapprovalcode(String creditcardapprovalcode) {
		this.creditcardapprovalcode = creditcardapprovalcode;
	}

	public void setCurrencyrate(Currencyrate currencyrate) {
		this.currencyrate = currencyrate;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDuedate(LocalDate duedate) {
		this.duedate = duedate;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public void setModifieddate(LocalDate modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setOnlineorderflag(String onlineorderflag) {
		this.onlineorderflag = onlineorderflag;
	}

	public void setOrderdate(LocalDate orderdate) {
		this.orderdate = orderdate;
	}

	public void setPurchaseordernumber(String purchaseordernumber) {
		this.purchaseordernumber = purchaseordernumber;
	}

	public void setRevisionnumber(Integer revisionnumber) {
		this.revisionnumber = revisionnumber;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

	public void setSalesorderdetails(List<Salesorderdetail> salesorderdetails) {
		this.salesorderdetails = salesorderdetails;
	}

	public void setSalesorderheadersalesreasons(List<Salesorderheadersalesreason> salesorderheadersalesreasons) {
		this.salesorderheadersalesreasons = salesorderheadersalesreasons;
	}

	public void setSalesorderid(Integer salesorderid) {
		this.salesorderid = salesorderid;
	}

	public void setSalesperson(Salesperson salesperson) {
		this.salesperson = salesperson;
	}

	public void setSalesterritory(Salesterritory salesterritory) {
		this.salesterritory = salesterritory;
	}

	public void setShipdate(LocalDate shipdate) {
		this.shipdate = shipdate;
	}

	public void setShipmethodid(Integer shipmethodid) {
		this.shipmethodid = shipmethodid;
	}

	public void setShiptoaddressid(Integer shiptoaddressid) {
		this.shiptoaddressid = shiptoaddressid;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public void setTaxamt(BigDecimal taxamt) {
		this.taxamt = taxamt;
	}

	public void setTotaldue(BigDecimal totaldue) {
		this.totaldue = totaldue;
	}

}