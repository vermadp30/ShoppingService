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
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.stickyio.util.CustomerConstants.TRACK_ORDER_REQUEST_TOPIC;

@Service
@Slf4j
public class TrackingService {
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    ExternalTrackingService externalTrackingService;

    @KafkaListener(topics = TRACK_ORDER_REQUEST_TOPIC, groupId = "shoppingGroup")
    @SendTo
    public TrackingResponseDto receiveTrackingRequest(TrackingRequestDto trackingRequest)
            throws ExecutionException, InterruptedException, TimeoutException {
        log.info(String.format("Track Order Request Received: %s",trackingRequest.toString()));
        TrackingResponseDto trackingResponse=externalTrackingService.
                createExternalTrackingRequest(trackingRequest.getOrderId());
        courierRepository.findCourierByOrderId(trackingRequest.getOrderId())
                .ifPresent(
                        courier -> {
                            trackingResponse.setCurrentStatus(courier.getCurrentStatus());
                            trackingResponse.setIsDelivered(courier.getIsDelivered());
                        }
                );
        return trackingResponse;
    }
}
