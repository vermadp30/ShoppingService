/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import static com.stickyio.util.CustomerConstants.TRACK_ORDER_REQUEST_TOPIC;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import com.stickyio.repository.CourierRepository;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

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
    log.info(String.format("Track Order Request Received: %s", trackingRequest.toString()));
    TrackingResponseDto trackingResponse = externalTrackingService.
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
