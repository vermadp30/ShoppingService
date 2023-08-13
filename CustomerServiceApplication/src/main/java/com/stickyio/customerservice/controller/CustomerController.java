/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stickyio.customerservice.dao.Customer;
import com.stickyio.customerservice.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Long id=customerService.createCustomer(customer);
        customer.setId(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{emailId}")
    Customer getCustomer(@PathVariable String emailId){
        return  customerService.getCustomer(emailId);
    }
}
