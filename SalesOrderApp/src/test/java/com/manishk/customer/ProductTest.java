package com.manishk.customer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.manishk.product.ProductRepository;

public class ProductTest {
	
	@Autowired
	ProductRepository productRepository;
	
	@Test
	public void testBootStrap(){
	}

}
