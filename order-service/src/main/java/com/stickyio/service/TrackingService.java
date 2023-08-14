/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dao.Courier;
import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import com.stickyio.repository.CourierRepository;
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
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    ExternalTrackingService externalTrackingService;
    private KafkaTemplate<String, TrackingRequestDto> kafkaTrackingServiceTemplate;

    public TrackingService(KafkaTemplate<String, TrackingRequestDto> kafkaTemplate) {

        this.kafkaTrackingServiceTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "track-order-request", groupId = "shoppingGroup")
    public void receiveTrackingRequest(TrackingRequestDto trackingRequest)
    {
        log.info(String.format("Track Order Request Received: %s",trackingRequest.toString()));
        externalTrackingService.createExternalTrackingRequest(trackingRequest.getOrderId());
        sendTrackingReply(trackingRequest.getOrderId());
    }

    public void sendTrackingReply(Long orderId) {
        log.info(String.format("Responding for tracking request for order: %s",orderId));
        courierRepository.findCourierByOrderId(orderId)
                .ifPresent(
                        courier -> {
                            kafkaTrackingServiceTemplate.send(
                                    MessageBuilder
                                            .withPayload(new TrackingResponseDto(
                                                    orderId,
                                                    courier.getCurrentStatus(),
                                                    courier.getIsDelivered()
                                            ))
                                            .setHeader(KafkaHeaders.TOPIC,"track-order-reply")
                                            .build()
                            );
                        }
                );
    }
}
