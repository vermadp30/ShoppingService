/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stickyio.dao.Customer;
import com.stickyio.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Long createCustomer(Customer customer) {
        customer=customerRepository.save(customer);
        return customer.getId();
    }

    public Customer getCustomer(String emailId){

        return customerRepository.getByEmailId(emailId);
    }

}

