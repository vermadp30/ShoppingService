/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import com.stickyio.repository.CourierRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalTrackingService {

    @Autowired
    CourierRepository courierRepository;
    private KafkaTemplate<String, TrackingRequestDto> kafkaTemplate;

    public ExternalTrackingService(KafkaTemplate<String, TrackingRequestDto> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void createExternalTrackingRequest(Long orderId) {
        log.info(String.format("Tracking for: %s",orderId));
        Message<TrackingRequestDto> message = MessageBuilder
                .withPayload(new TrackingRequestDto(orderId))
                .setHeader(KafkaHeaders.TOPIC,"track-order-ext-request")
                .build();

        kafkaTemplate.send(message);
    }

    @Transactional
    @KafkaListener(topics = "track-order-ext-reply", groupId = "shoppingGroup")
    public void receiveExternalTrackingReply(TrackingResponseDto trackingResponse) {
        log.info(String.format("Received Tracking Response: %s", trackingResponse.toString()));
        courierRepository.updateCourierStatusAndIsDeliveredByOrderId(
                trackingResponse.getOrderId(),
                trackingResponse.getCurrentStatus(),
                trackingResponse.getIsDelivered()
        );
    }
}
