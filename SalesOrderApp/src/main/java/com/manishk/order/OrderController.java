package com.manishk.order;

import java.util.List;

import org.hibernate.metamodel.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="order")
public class OrderController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public SalesOrder add(@RequestBody SalesOrder order) {
		log.info("saving "+order);
		if (order == null)
			throw new ValidationException("Invalid input");
		return orderService.add(order);

	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<SalesOrder> allOrders() {
		try {
			return orderService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public SalesOrder order(
			@PathVariable(value = "orderId") String orderId) {
		try {
			return orderService.get(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public SalesOrder update(@RequestBody SalesOrder order) {
		log.info("updating:: "+order);
		if (order == null || order(order.getOrderNumber())==null)
			throw new ValidationException("Invalid input");
		return orderService.update(order);
	}

	@RequestMapping(value="/{orderId}",method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public boolean delete(@PathVariable("orderId") String orderId) {
		if (orderId == null)
			throw new ValidationException("Invalid input");
		return orderService.delete(orderId);
	}
	
}
