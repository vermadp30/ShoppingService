/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.controller;

import com.stickyio.dao.Customer;
import com.stickyio.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @PostMapping
  ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    Long id = customerService.createCustomer(customer);
    customer.setId(id);
    return ResponseEntity.ok(customer);
  }

  @GetMapping
  Customer getCustomer(@RequestParam String email) {
    return customerService.getCustomer(email);
  }
}
