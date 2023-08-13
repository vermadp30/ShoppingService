/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.controller;

import com.stickyio.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stickyio.service.OrderService;
import com.stickyio.dto.OrderRequestDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    TrackingService trackingService;

    @PostMapping
    void createOrder(@RequestBody OrderRequestDto order) {

        orderService.createOrder(order);
    }

    @GetMapping("/track/{orderId}")
    Optional<String> trackOrder(@PathVariable Long orderId) throws InterruptedException {
        return trackingService.createTrackingRequest(orderId);
    }
}
