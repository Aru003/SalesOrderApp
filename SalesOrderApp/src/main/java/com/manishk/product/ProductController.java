package com.manishk.product;

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
@RequestMapping("product")
public class ProductController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	ProductRepository repository;
	
	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.repository = productRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<Product> products() {
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Product add(@RequestBody Product product) {
		log.info("saving "+product);
		if (product == null)
			throw new ValidationException("Invalid input");
		return repository.save(product);

	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Product customer(
			@PathVariable(value = "code") String customerId) {
		return repository.find(customerId);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Product update(@RequestBody Product product) {
		log.info("updating:: "+product);
		if (product == null || customer(product.getCode())==null)
			throw new ValidationException("Invalid input");
		return repository.save(product);
	}

	@RequestMapping(value="/{code}",method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Product delete(@PathVariable("code") String code) {
		Product product = repository.find(code);
		if (product == null)
			throw new ValidationException("Customer not found");
		product.setDeleted(true);
		return repository.save(product);
	}

}
