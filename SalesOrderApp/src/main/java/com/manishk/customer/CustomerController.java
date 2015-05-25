package com.manishk.customer;

import java.util.List;

import javax.persistence.PersistenceException;

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
@RequestMapping(value = "customer")
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	private CustomerRepository repository;

	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.repository = customerRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<Customer> customers() {
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Customer add(@RequestBody Customer customer) {
		log.info("saving "+customer);
		if (customer == null)
			throw new ValidationException("Invalid input");
		return repository.save(customer);

	}

	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Customer customer(
			@PathVariable(value = "customerId") String customerId) {
		return repository.find(customerId);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Customer update(@RequestBody Customer customer) {
		log.info("updating:: "+customer);
		if (customer == null || customer(customer.getCode())==null)
			throw new ValidationException("Invalid input");
		return repository.save(customer);
	}

	@RequestMapping(value="/{customerId}",method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Customer delete(@PathVariable("customerId") String customerId) {
		Customer customer = repository.find(customerId);
		if (customer == null)
			throw new ValidationException("Customer not found");
		customer.setDeleted(true);
		return repository.save(customer);
	}

}
