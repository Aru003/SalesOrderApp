package com.manishk.customer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerRepository {
	private static final Logger log = LoggerFactory.getLogger(CustomerRepository.class);
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Customer save(Customer customer){
		try {
			log.info("Saving "+customer);
			entityManager.persist(customer);
			return customer;
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public boolean delete(Customer customer){
		try {
			log.info("Deleting "+customer);
			entityManager.remove(customer);
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Customer> findAll(){
		try {
			return entityManager.createQuery("select c from Customer c where c.deleted=false",Customer.class).getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Customer find(String customerId){
		log.info("find "+customerId);
		try {
			return entityManager.createNamedQuery(Customer.FIND_BY_CODE,Customer.class).setParameter("customerId", customerId).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

}
