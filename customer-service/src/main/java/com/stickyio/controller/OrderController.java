/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.controller;

import com.stickyio.dao.CustomerOrderMapping;
import com.stickyio.dto.OrderRequestDto;
import com.stickyio.repository.CustomerOrderRepository;
import com.stickyio.service.OrderService;
import com.stickyio.service.TrackingService;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

  @Autowired
  OrderService orderService;
  @Autowired
  TrackingService trackingService;

  @Autowired
  CustomerOrderRepository customerOrderRepository;

  @PostMapping
  String createOrder(@RequestBody OrderRequestDto order)
      throws InterruptedException, ExecutionException, TimeoutException {
    return orderService.createOrder(order);
  }

  @GetMapping
  List<CustomerOrderMapping> getOrdersForCustomer(@RequestParam String email) {
    return orderService.getOrderByEmail(email);
  }

  @GetMapping("/track/{orderId}")
  ResponseEntity<CustomerOrderMapping> trackOrder(@PathVariable Long orderId)
      throws InterruptedException, ExecutionException, TimeoutException {
    CustomerOrderMapping customerOrderMapping = trackingService.createTrackingRequest(orderId);
    return ResponseEntity.ok(customerOrderMapping);
  }

  @PostMapping("/status/{orderId}")
  @Transactional
  int trackOrder(@PathVariable Long orderId, @RequestParam String status) {
    return customerOrderRepository.updateCourierStatusByOrderIdAndDate(orderId, status, new Date());
  }
}
