/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dao.Courier;
import com.stickyio.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stickyio.dao.Order;
import com.stickyio.dao.OrderStatus;
import com.stickyio.dto.OrderReplyDto;
import com.stickyio.dto.OrderRequestDto;
import com.stickyio.repository.OrderRepository;

import static com.stickyio.util.CustomerConstants.CREATE_ORDER_TOPIC;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CourierRepository courierRepository;

    @KafkaListener(topics = CREATE_ORDER_TOPIC, groupId = "shoppingGroup")
    @SendTo
    OrderReplyDto createOrder(OrderRequestDto orderRequest) throws InterruptedException{
        log.info(String.format("Create Order Request Received: %s",orderRequest.toString()));
        Order order=new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setItem(orderRequest.getItem());
        order.setStatus(OrderStatus.ORDER_PLACED);
        order=orderRepository.save(order);
        addCourierDetails(order);
        OrderReplyDto orderReply=new OrderReplyDto(
                order.getCustomerId(),
                order.getId(),
                order.getStatus());
        return orderReply;
    }

    public void addCourierDetails(Order order)
    {
        courierRepository.save(new Courier(
                order.getId(),
                "Order Received",
                false
                ));
    }
}
