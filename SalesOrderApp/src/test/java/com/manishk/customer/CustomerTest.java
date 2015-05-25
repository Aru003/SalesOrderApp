package com.manishk.customer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.manishk.config.WebAppConfigurationAware;

public class CustomerTest extends WebAppConfigurationAware {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	public void testBootStrap(){
		Assert.assertNotNull(customerRepository);
	}

}
