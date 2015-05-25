package com.manishk.customer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.manishk.config.WebAppConfigurationAware;
import com.manishk.order.LineItem;
import com.manishk.order.OrderRepository;
import com.manishk.order.OrderService;
import com.manishk.order.SalesOrder;
import com.manishk.product.Product;
import com.manishk.product.ProductRepository;

public class OrderTest extends WebAppConfigurationAware {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderService orderService;

	@Test
	public void testOrderPersist() {
		List<Customer> customers = customerRepository.findAll();
		SalesOrder order = new SalesOrder();

		order.setCustomer(customers.get(0).getCode());
		order.setDeleted(false);
		order.setOrderNumber("123");
		order.setTotalPrice(customers.get(0).getCreditLimit()-2.00);

		List<LineItem> items = new ArrayList<LineItem>();
		LineItem item = new LineItem();
		item.setOrderNumber(order.getOrderNumber());
		;
		List<Product> products = productRepository.findAll();
		item.setProduct(products.get(0).getCode());
		item.setQuantity(1);
		item.setUnitPrice(2.0);
		item.setTotalPrice(2.0);
		items.add(item);
		order.setLineItems(items);
		try {
			order = orderRepository.save(order);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof PersistenceException);
		}
		Assert.assertNotNull(order);
	}

	@Test
	public void testOrderDelete() {
		SalesOrder order = createOrder("124");
		try {
			orderRepository.delete("124");
			orderRepository.find("124");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof PersistenceException);
		}
	}
	
	@Test
	public void testOrderfind() {
		SalesOrder order = createOrder("1245");
		order = orderRepository.find("1245");
		Assert.assertNotNull(order);
		Assert.assertNotNull(order.getLineItems());
		Assert.assertTrue(order.getLineItems().size() == 1);
	}

	private SalesOrder createOrder(String orderId) {
		List<Customer> customers = customerRepository.findAll();
		SalesOrder order = new SalesOrder();

		order.setCustomer(customers.get(0).getCode());
		order.setDeleted(false);
		order.setOrderNumber(orderId);
		order.setTotalPrice(10.00);

		List<LineItem> items = new ArrayList<LineItem>();
		LineItem item = new LineItem();
		item.setOrderNumber(order.getOrderNumber());
		;
		List<Product> products = productRepository.findAll();
		item.setProduct(products.get(0).getCode());
		item.setQuantity(1);
		item.setUnitPrice(2.0);
		item.setTotalPrice(2.0);
		items.add(item);
		order.setLineItems(items);
		try {
			order = orderRepository.save(order);
		} catch (Exception e) {

		}
		return order;
	}
}
