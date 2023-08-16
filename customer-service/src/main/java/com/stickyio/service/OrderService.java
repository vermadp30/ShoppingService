/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dao.CustomerOrderMapping;
import com.stickyio.dto.OrderReplyDto;
import com.stickyio.dto.OrderRequestDto;
import com.stickyio.repository.CustomerOrderRepository;
import com.stickyio.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.stickyio.util.CustomerConstants.CREATE_ORDER_REPLY_TOPIC;
import static com.stickyio.util.CustomerConstants.CREATE_ORDER_TOPIC;

@Service
@Slf4j
public class OrderService {
    @Autowired
    CustomerOrderRepository customerOrderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ReplyingKafkaTemplate<String, OrderRequestDto, OrderReplyDto> kafkaTemplateForOrder;

    public String createOrder(OrderRequestDto order) throws InterruptedException, ExecutionException, TimeoutException {
        if (!kafkaTemplateForOrder.waitForAssignment(Duration.ofSeconds(10))) {
            throw new IllegalStateException("Reply container did not initialize");
        }
        log.info(String.format("Create Order Request: %s", order.toString()));
        ProducerRecord<String, OrderRequestDto> record = new ProducerRecord<>(
                CREATE_ORDER_TOPIC, order);
        record.headers().add(new RecordHeader(
                KafkaHeaders.REPLY_TOPIC, CREATE_ORDER_REPLY_TOPIC.getBytes()));
        RequestReplyFuture<String, OrderRequestDto, OrderReplyDto> sendAndReceive =
                kafkaTemplateForOrder.sendAndReceive(record, Duration.ofSeconds(10));
        SendResult<String, OrderRequestDto> sendResult = sendAndReceive.getSendFuture().get();
        ConsumerRecord<String, OrderReplyDto> consumerRecord = sendAndReceive.get();
        log.info(String.format("Order created : %s", consumerRecord.value().toString()));
        customerOrderRepository.save(new CustomerOrderMapping(
                consumerRecord.value().getCustomerId(), consumerRecord.value().getOrderId()));
        return String.format("Order for item '%s' generated with Id '%s' for the customer.",
                order.getItem(),
                consumerRecord.value().getOrderId());
    }

    public List<CustomerOrderMapping> getOrderByEmail(String email) {
        return customerOrderRepository.findByCustomerId(
                customerRepository.getByEmail(email)
                        .getId());
    }
}
