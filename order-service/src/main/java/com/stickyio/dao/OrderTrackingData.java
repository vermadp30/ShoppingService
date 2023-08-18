/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order-tracking-data")
public class OrderTrackingData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderId;

  private String currentStatus;

  private Boolean isDelivered;

  private Date createdOn;

  public OrderTrackingData(Long orderId, String currentStatus, Boolean isDelivered,
      Date createdOn) {
    this.orderId = orderId;
    this.currentStatus = currentStatus;
    this.isDelivered = isDelivered;
    this.createdOn = createdOn;
  }
}
