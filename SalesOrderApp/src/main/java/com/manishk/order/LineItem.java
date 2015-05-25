package com.manishk.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.manishk.product.Product;

@Entity
@Table(name="LINE_ITEM")
public class LineItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="LINE_ITEM_ID")
	private Integer lineItemId;
	
	@Column(name="SALES_ORDER_NO",nullable=false)
	private String orderNumber;

	@Column(name = "PRODUCT", nullable = false)
	private String product;
	
	@Column(name="QUANTITY")
	private Integer quantity;
	
	@Column(name="UNIT_PRICE")
	private Double unitPrice;
	
	@Column(name="TOTAL_PRICE")
	private Double totalPrice;
	

	
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String productCode) {
		this.product = productCode;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public int hashCode() {
		return (lineItemId).hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineItem other = (LineItem) obj;
		return lineItemId.equals(other.lineItemId);
	}

	/**
	 * @return the lineItemId
	 */
	public Integer getLineItemId() {
		return lineItemId;
	}

	/**
	 * @param lineItemId the lineItemId to set
	 */
	public void setLineItemId(Integer lineItemId) {
		this.lineItemId = lineItemId;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNumber(String orderNo) {
		this.orderNumber = orderNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LineItem [lineItemId=" + lineItemId + ", orderNumber="
				+ orderNumber + ", product=" + product + ", quantity="
				+ quantity + ", unitPrice=" + unitPrice + ", totalPrice="
				+ totalPrice + "]";
	}

}
