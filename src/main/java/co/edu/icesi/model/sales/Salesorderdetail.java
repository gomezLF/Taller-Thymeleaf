package co.edu.icesi.model.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the salesorderdetail database table.
 *
 */
@Entity
@NamedQuery(name = "Salesorderdetail.findAll", query = "SELECT s FROM Salesorderdetail s")
public class Salesorderdetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SALESORDERDETAIL_SALESORDERDETAILID_GENERATOR", allocationSize = 1, sequenceName = "SALESORDERDETAIL_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALESORDERDETAIL_SALESORDERDETAILID_GENERATOR")
	private Integer id;
	
	private String carriertrackingnumber;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "La fecha de modificación debe ser la actual")
	private LocalDate modifieddate;

	private Integer orderqty;

	private Integer rowguid;
	
	@NotNull(message = "El unitprice no debe estar vacio")
	@Min(value = 1, message = "El precio unitario debe ser mayor a 1")
	private BigDecimal unitprice;
	
	@NotNull(message = "El descuento no debe estar vacio")
	@Min(value = 0, message = "El descuento debe ser mayor o igual a 0")
	private BigDecimal unitpricediscount;

	// bi-directional many-to-one association to Salesorderheader
	@ManyToOne
	@JoinColumn(name = "salesorderid", insertable = false, updatable = false)
	private Salesorderheader salesorderheader;

	// bi-directional many-to-one association to Specialofferproduct
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "productid", referencedColumnName = "productid", insertable = false, updatable = false),
			@JoinColumn(name = "specialofferid", referencedColumnName = "specialofferid", insertable = false, updatable = false) })
	private Specialofferproduct specialofferproduct;

	public Salesorderdetail() {
		setModifieddate(LocalDate.now());
	}

	public String getCarriertrackingnumber() {
		return this.carriertrackingnumber;
	}

	public Integer getId() {
		return this.id;
	}

	public LocalDate getModifieddate() {
		return this.modifieddate;
	}

	public Integer getOrderqty() {
		return this.orderqty;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public Salesorderheader getSalesorderheader() {
		return this.salesorderheader;
	}

	public Specialofferproduct getSpecialofferproduct() {
		return this.specialofferproduct;
	}

	public BigDecimal getUnitprice() {
		return this.unitprice;
	}

	public BigDecimal getUnitpricediscount() {
		return this.unitpricediscount;
	}

	public void setCarriertrackingnumber(String carriertrackingnumber) {
		this.carriertrackingnumber = carriertrackingnumber;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setModifieddate(LocalDate modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

	public void setSalesorderheader(Salesorderheader salesorderheader) {
		this.salesorderheader = salesorderheader;
	}

	public void setSpecialofferproduct(Specialofferproduct specialofferproduct) {
		this.specialofferproduct = specialofferproduct;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	public void setUnitpricediscount(BigDecimal unitpricediscount) {
		this.unitpricediscount = unitpricediscount;
	}

}