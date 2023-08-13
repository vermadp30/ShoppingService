/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stickyio.customerservice.service.OrderService;
import com.stickyio.orderservice.dto.OrderRequestDto;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    void createOrder(@RequestBody OrderRequestDto order) {

        orderService.createOrder(order);
    }
}
