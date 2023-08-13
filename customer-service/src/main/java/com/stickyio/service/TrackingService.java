/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import com.stickyio.repository.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TrackingService {
    private KafkaTemplate<String, TrackingRequestDto> kafkaTemplate;

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    public TrackingService(KafkaTemplate<String, TrackingRequestDto> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public Optional<String> createTrackingRequest(Long orderId) throws InterruptedException {
        log.info(String.format("Tracking for: %s",orderId));
        Message<TrackingRequestDto> message = MessageBuilder
                .withPayload(new TrackingRequestDto(orderId))
                .setHeader(KafkaHeaders.TOPIC,"track-order-request")
                .build();
        kafkaTemplate.send(message);
        wait(1000);
        return customerOrderRepository.getCurrentStatusByOrderId(orderId);
    }

    @KafkaListener(topics = "track-order-reply")
    public void receiveTrackingReply(TrackingResponseDto trackingResponse) {
        log.info(String.format("Received Tracking Response: %s", trackingResponse.toString()));
        customerOrderRepository.updateCourierStatusByOrderId(
                trackingResponse.getOrderId(),
                trackingResponse.getCurrentStatus());
    }
}
