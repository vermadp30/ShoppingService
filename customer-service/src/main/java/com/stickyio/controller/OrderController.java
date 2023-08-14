/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.controller;

import com.stickyio.dao.CustomerOrderMapping;
import com.stickyio.repository.CustomerOrderRepository;
import com.stickyio.service.TrackingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stickyio.service.OrderService;
import com.stickyio.dto.OrderRequestDto;

import java.util.List;
import java.util.Optional;

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
    String createOrder(@RequestBody OrderRequestDto order){
        return orderService.createOrder(order);
    }

    @GetMapping
    List<CustomerOrderMapping> getOrdersForCustomer(@RequestParam String email){
        return orderService.getOrderByEmail(email);
    }

    @GetMapping("/track/{orderId}")
    ResponseEntity<CustomerOrderMapping> trackOrder(@PathVariable Long orderId){
        CustomerOrderMapping customerOrderMapping= trackingService.createTrackingRequest(orderId);
        return ResponseEntity.ok(customerOrderMapping);
    }

    @PostMapping("/status/{orderId}")
    @Transactional
    int trackOrder(@PathVariable Long orderId, @RequestParam String status){
        return customerOrderRepository.updateCourierStatusByOrderId(orderId,status);
    }
}
