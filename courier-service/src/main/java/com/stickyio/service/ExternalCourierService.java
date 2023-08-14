/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalCourierService {
    private final KafkaTemplate<String, TrackingResponseDto> kafkaReplyTemplate;

    public ExternalCourierService(KafkaTemplate<String, TrackingResponseDto> kafkaReplyTemplate) {
        this.kafkaReplyTemplate = kafkaReplyTemplate;
    }

    @KafkaListener(topics = "track-order-ext-request", groupId = "shoppingGroup")
    public void processOrderStatusRequest(TrackingRequestDto trackingRequest) {
        log.info(String.format("Received Tracking Request for order: %s",trackingRequest.getOrderId()));
        String location = generateRandomStatus(trackingRequest.getOrderId());
        String status="Packet arrived at " + location + " hub";
        TrackingResponseDto trackingResponse=new TrackingResponseDto(
                trackingRequest.getOrderId(),
                status,
                false
        );
        log.info(String.format("Sending Status as %s for Order Id: %s",
                trackingResponse.getCurrentStatus(),
                trackingResponse.getOrderId()));
        Message<TrackingResponseDto> message = MessageBuilder
                .withPayload(trackingResponse)
                .setHeader(KafkaHeaders.TOPIC,"track-order-ext-reply")
                .build();
        kafkaReplyTemplate.send(message);
    }

    private String generateRandomStatus(Long orderNumber) {
        String[] cities = {"Mumbai", "Surat", "Ratlam", "Kota", "Jaipur", "New Delhi"};
        String randomCity = cities[(int) (Math.random() * cities.length)];
        return randomCity;
    }
}
