package co.edu.icesi.model.sales;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the salesorderdetail database table.
 * 
 */
@Embeddable
public class SalesorderdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="SALES_ORDER_ID", insertable=false, updatable=false)
	private Integer salesorderid;
	
	@Column(name="SALES_ORDER_DETAIL_ID", insertable=false, updatable=false)
	private Integer salesorderdetailid;

	public SalesorderdetailPK() {
	}
	public Integer getSalesorderid() {
		return this.salesorderid;
	}
	public void setSalesorderid(Integer salesorderid) {
		this.salesorderid = salesorderid;
	}
	public Integer getSalesorderdetailid() {
		return this.salesorderdetailid;
	}
	public void setSalesorderdetailid(Integer salesorderdetailid) {
		this.salesorderdetailid = salesorderdetailid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SalesorderdetailPK)) {
			return false;
		}
		SalesorderdetailPK castOther = (SalesorderdetailPK)other;
		return 
			this.salesorderid.equals(castOther.salesorderid)
			&& this.salesorderdetailid.equals(castOther.salesorderdetailid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.salesorderid.hashCode();
		hash = hash * prime + this.salesorderdetailid.hashCode();
		
		return hash;
	}
}