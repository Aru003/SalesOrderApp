package com.manishk.order;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="SALES_ORDER")
@NamedQueries({@NamedQuery(name=SalesOrder.SALES_ORDER_FIND,query="select s from SalesOrder s inner join fetch s.lineItems li where s.orderNumber=:orderNumber and s.deleted=false and li.orderNumber=s.orderNumber"),@NamedQuery(name=SalesOrder.SALES_ORDER_FINDAll,query="select s from SalesOrder s inner join fetch s.lineItems li where s.deleted=false and li.orderNumber=s.orderNumber")})
public class SalesOrder {
	
	public static final String SALES_ORDER_FIND = "SalesOrder.find";
	public static final String SALES_ORDER_FINDAll = "SalesOrder.findAll";
	
	@Id
	@Column(name="ORDER_NO",unique=true,nullable=false)
	private String orderNumber;
	
	@Column(name = "CUSTOMER", nullable = false)
	private String customer;
	
	@Column(name="TOTAL_PRICE")
	private Double totalPrice;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="orderNumber")
	@Cascade({CascadeType.PERSIST,CascadeType.MERGE})
	private List<LineItem> lineItems;
	
	@Column(name="DELETED")
	private boolean deleted;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public int hashCode() {
		return orderNumber.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesOrder other = (SalesOrder) obj;
		return orderNumber.equals(other.orderNumber);
	}
	/**
	 * @return the lineItems
	 */
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SalesOrder [orderNumber=" + orderNumber + ", customer="
				+ customer + ", totalPrice=" + totalPrice + ", lineItems="
				+ lineItems + ", deleted=" + deleted + "]";
	}
	
	

}
