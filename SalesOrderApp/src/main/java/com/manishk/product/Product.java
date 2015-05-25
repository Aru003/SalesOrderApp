package com.manishk.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "PRODUCT")
@NamedQuery(name = Product.FIND_BY_CODE, query = "select p from Product p where p.code = :code and p.deleted=false")
public class Product {
	
	public static final String FIND_BY_CODE = "Product.findByCode";
	
	@Id
	private String code;

	private String description;
	private Double price = 0.0;
	private Integer quantity = 0;
	private boolean deleted = false;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public int hashCode() {
		return code.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Product [code=" + code + ", description=" + description
				+ ", price=" + price + ", quantity=" + quantity + ", deleted="
				+ deleted + "]";
	}
}
