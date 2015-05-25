package com.manishk.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "CUSTOMER")
@NamedQuery(name = Customer.FIND_BY_CODE, query = "select c from Customer c where c.code = :customerId and c.deleted=false")
public class Customer {
	
	public static final String FIND_BY_CODE = "Customer.findByCode";
	
	@Id
	private String code;
	
	@Column(name="NAME",nullable=false)
	private String name;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="PHONE1")
	private String phone1;
	
	@Column(name="PHONE2")
	private String phone2;
	
	@Column(name="CREDIT_LIMIT",nullable=false)
	private Double creditLimit=0.0;
	
	@Column(name="CURRENT_CREDIT_LIMIT",nullable=false)
	private Double currentCreditLimit=0.0;
	
	@Column(name="DELETED",nullable=false)
	private boolean deleted=false;
	
	public Customer(){
		
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Double getCurrentCreditLimit() {
		return currentCreditLimit;
	}
	public void setCurrentCreditLimit(Double currentCreditLimit) {
		this.currentCreditLimit = currentCreditLimit;
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
		Customer other = (Customer) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [code=" + code + ", name=" + name + ", address="
				+ address + ", phone1=" + phone1 + ", phone2=" + phone2
				+ ", creditLimit=" + creditLimit + ", currentCreditLimit="
				+ currentCreditLimit + ", deleted=" + deleted + "]";
	}
	
	
	
	

}
