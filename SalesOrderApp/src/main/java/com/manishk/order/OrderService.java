package com.manishk.order;

import java.util.List;

import org.hibernate.metamodel.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.manishk.customer.Customer;
import com.manishk.customer.CustomerRepository;
import com.manishk.product.Product;
import com.manishk.product.ProductRepository;

@Service
@Transactional
public class OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
	
	private ProductRepository productRepository;
	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;

	@Autowired
	public OrderService(CustomerRepository customerRepository ,ProductRepository productRepository, OrderRepository orderRepository) {
		this.customerRepository= customerRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public SalesOrder add(SalesOrder order) {
		log.info("saving "+order);
		validateOrder(order);
		return orderRepository.save(order);

	}
	
	public SalesOrder get(String orderId) {
		try {
			return orderRepository.find(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public SalesOrder update(SalesOrder order) {
		log.info("updating:: "+order);
		validateOrder(order);
		return orderRepository.save(order);
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
	public boolean delete( String orderId) {
		SalesOrder order = get(orderId);
		releaseInventoryAndCredit(order);
		return orderRepository.delete(orderId);
	}

	private void validateOrder(SalesOrder order){
		if(order == null || order.getOrderNumber()==null || order.getLineItems()==null||order.getLineItems().isEmpty())
			throw new ValidationException("Invalid Order");
		Customer customer = customerRepository.find(order.getCustomer());
		Double availableCredit = customer.getCreditLimit() - customer.getCurrentCreditLimit();
		if(availableCredit < order.getTotalPrice())
			throw new ValidationException("Insufficient credit limit");
		for(LineItem item : order.getLineItems()){
			item.setOrderNumber(order.getOrderNumber());
			Product product = productRepository.find(item.getProduct());
			if(product.getQuantity() < item.getQuantity())
				throw new ValidationException("Insufficient available quantity for product:"+product.getCode());
		}
		//Reduce the available credit limit now
		customer.setCurrentCreditLimit(customer.getCurrentCreditLimit()+order.getTotalPrice());
		
		//Reduce available quantity of Products
		for(LineItem item : order.getLineItems()){
			Product product = productRepository.find(item.getProduct());
			product.setQuantity(product.getQuantity()-item.getQuantity());
		}
	}
	
	private void releaseInventoryAndCredit(SalesOrder order){
		if(order == null || order.getOrderNumber()==null || order.getLineItems()==null||order.getLineItems().isEmpty())
			throw new ValidationException("Invalid Order");
		Customer customer = customerRepository.find(order.getCustomer());
		customer.setCurrentCreditLimit(customer.getCurrentCreditLimit()-order.getTotalPrice());
		for(LineItem item : order.getLineItems()){
			Product product = productRepository.find(item.getProduct());
			product.setQuantity(product.getQuantity()+item.getQuantity());
		}
	}

	public List<SalesOrder> getAll() {
		try {
			return orderRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
