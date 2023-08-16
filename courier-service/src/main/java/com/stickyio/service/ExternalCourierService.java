/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalCourierService {

  @KafkaListener(topics = "track-order-ext-request", groupId = "shoppingGroup")
  @SendTo
  public TrackingResponseDto processOrderStatusRequest(TrackingRequestDto trackingRequest) {
    log.info(
        String.format("Received Tracking Request for order: %s", trackingRequest.getOrderId()));
    String location = generateRandomStatus(trackingRequest.getOrderId());
    String status = "Packet arrived at " + location + " hub";
    TrackingResponseDto trackingResponse = new TrackingResponseDto(
        trackingRequest.getOrderId(),
        status,
        false
    );
    log.info(String.format("Sending Status as %s for Order Id: %s",
        trackingResponse.getCurrentStatus(),
        trackingResponse.getOrderId()));
    return trackingResponse;
  }

  private String generateRandomStatus(Long orderNumber) {
    String[] cities = {"Mumbai", "Surat", "Ratlam", "Kota", "Jaipur", "New Delhi"};
    String randomCity = cities[(int) (Math.random() * cities.length)];
    return randomCity;
  }
}
