/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stickyio.orderservice.dao.Order;
import com.stickyio.orderservice.dao.OrderStatus;
import com.stickyio.orderservice.dto.OrderReplyDto;
import com.stickyio.orderservice.dto.OrderRequestDto;
import com.stickyio.orderservice.repository.OrderRepository;

@Service
@Slf4j
public class OrderService {
    public static Logger LOGGER= LoggerFactory.getLogger(OrderService.class);

    private KafkaTemplate<String, OrderReplyDto> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderReplyDto> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    OrderRepository orderRepository;

    @KafkaListener(topics = "create-order", groupId = "shoppingGroup")
    void createOrder(OrderRequestDto orderRequest)
    {
        log.info(String.format("Create Order Request Received: %s",orderRequest.toString()));
        Order order=new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setItem(orderRequest.getItem());
        order.setStatus(OrderStatus.ORDER_PLACED);
        order=orderRepository.save(order);
        createOrderReply(order);
    }

    public void createOrderReply(Order order){
        log.info(String.format("Order created with order id: %s", order.getId()));

        OrderReplyDto orderReply=new OrderReplyDto(
                order.getCustomerId(),
                order.getId(),
                order.getStatus());

        Message<OrderReplyDto> message = MessageBuilder
                .withPayload(orderReply)
                .setHeader(KafkaHeaders.TOPIC,"create-order-reply")
                .build();

        kafkaTemplate.send(message);
    }
}
