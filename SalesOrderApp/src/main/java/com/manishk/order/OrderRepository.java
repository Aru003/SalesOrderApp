package com.manishk.order;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderRepository {

	private static final Logger log = LoggerFactory
			.getLogger(OrderRepository.class);

	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SalesOrder save(SalesOrder order) {
		log.info("saving :: "+order);
		try {
			entityManager.persist(order);
			return order;
		} catch (PersistenceException e) {
			throw e;
		}
	}

	private boolean delete(SalesOrder order) {
		try {
			order.setDeleted(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional(readOnly = false)
	public boolean delete(String orderId) {
		log.info("delete :: "+orderId);
		SalesOrder order = find(orderId);
		if (order != null)
			return delete(order);
		return false;
	}

	@Transactional(readOnly=true)
	public SalesOrder find(String salesOrderId) {
		log.info("find :: "+salesOrderId);
		try {
			return entityManager.createNamedQuery(SalesOrder.SALES_ORDER_FIND,
					SalesOrder.class).setParameter("orderNumber", salesOrderId).getSingleResult();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	public List<SalesOrder> findAll() {
		log.info("findAll :: Orders ");
		try {
			return entityManager.createNamedQuery(SalesOrder.SALES_ORDER_FINDAll,
					SalesOrder.class).getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	public SalesOrder find2(String salesOrderId) {
		log.info("find :: "+salesOrderId);
		try {
			return entityManager.find(SalesOrder.class, salesOrderId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SalesOrder update(SalesOrder order){
		log.info("updating :: "+order);
		SalesOrder order2 = find(order.getOrderNumber());
		if(order2==null)
			return null;
		order2.setCustomer(order.getCustomer());
		order2.setTotalPrice(order.getTotalPrice());
		return order2;
	}

}
