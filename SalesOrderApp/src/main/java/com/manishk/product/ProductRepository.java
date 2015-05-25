package com.manishk.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ProductRepository {
	
	private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Product save(Product product){
		try {
			log.info("Saving "+product);
			entityManager.persist(product);
			return product;
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public boolean delete(Product product){
		try {
			log.info("Deleting "+product);
			entityManager.remove(product);
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Product> findAll(){
		try {
			return entityManager.createQuery("select p from Product p where p.deleted=false",Product.class).getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Product find(String code){
		log.info("find "+code);
		try {
			return entityManager.createNamedQuery(Product.FIND_BY_CODE,Product.class).setParameter("code", code).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}


}
