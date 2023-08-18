/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import static com.stickyio.util.CustomerConstants.CREATE_ORDER_TOPIC;

import com.stickyio.dao.Order;
import com.stickyio.dao.OrderStatus;
import com.stickyio.dao.OrderTrackingData;
import com.stickyio.dto.OrderReplyDto;
import com.stickyio.dto.OrderRequestDto;
import com.stickyio.repository.OrderRepository;
import com.stickyio.repository.OrderTrackingDataRepository;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderTrackingDataRepository orderTrackingDataRepository;

  @KafkaListener(topics = CREATE_ORDER_TOPIC, groupId = "shoppingGroup")
  @SendTo
  OrderReplyDto createOrder(OrderRequestDto orderRequest) throws InterruptedException {
    log.info(String.format("Create Order Request Received: %s", orderRequest.toString()));
    Order order = new Order();
    order.setCustomerId(orderRequest.getCustomerId());
    order.setItem(orderRequest.getItem());
    order.setStatus(OrderStatus.ORDER_PLACED);
    order.setUpdatedOn(orderRequest.getCreatedOn());
    order = orderRepository.save(order);
    addCourierDetails(order);
    return new OrderReplyDto(
        order.getCustomerId(),
        order.getId(),
        order.getStatus());
  }

  public void addCourierDetails(Order order) {
    orderTrackingDataRepository.save(new OrderTrackingData(
        order.getId(),
        "Order Received",
        false,
        new Date()
    ));
  }
}
