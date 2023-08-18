/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.service;

import static com.stickyio.util.CustomerConstants.TRACK_ORDER_REQUEST_TOPIC;

import com.stickyio.dao.OrderStatus;
import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import com.stickyio.repository.OrderRepository;
import com.stickyio.repository.OrderTrackingDataRepository;
import jakarta.transaction.Transactional;
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
  OrderTrackingDataRepository orderTrackingDataRepository;
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  ExternalTrackingService externalTrackingService;

  @Transactional
  @KafkaListener(topics = TRACK_ORDER_REQUEST_TOPIC, groupId = "shoppingGroup")
  @SendTo
  public TrackingResponseDto receiveTrackingRequest(TrackingRequestDto trackingRequest)
      throws ExecutionException, InterruptedException, TimeoutException {
    log.info(String.format("Track Order Request Received: %s", trackingRequest.toString()));
    TrackingResponseDto trackingResponse = externalTrackingService.
        createExternalTrackingRequest(trackingRequest.getOrderId());
    updateOrder(trackingResponse);
    return trackingResponse;
  }

  @Transactional
  private void updateOrder(TrackingResponseDto trackingResponse) {
    orderRepository.updateOrderStatusAndStatusDetailByOrderId(
        trackingResponse.getOrderId(),
        (trackingResponse.getIsDelivered() ? OrderStatus.ORDER_DELIVERED
            : OrderStatus.ORDER_ON_THE_WAY),
        trackingResponse.getCurrentStatus(),
        trackingResponse.getResponseTimestamp()
    );

  }
}
