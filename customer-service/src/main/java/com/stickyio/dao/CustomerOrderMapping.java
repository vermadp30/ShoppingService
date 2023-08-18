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
@Table(name = "customer-order-mappings")
@NoArgsConstructor
@Setter
@Getter
public class CustomerOrderMapping {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "customer-id", nullable = false)
  private Long customerId;

  @Column(name = "order-id", nullable = false, unique = true)
  private Long orderId;

  private String itemName;

  private String currentStatus;

  @Getter
  private Date updatedOn;

  public CustomerOrderMapping(Long customerId, Long orderId, String itemName, String currentStatus,
      Date updatedOn) {
    this.customerId = customerId;
    this.orderId = orderId;
    this.itemName = itemName;
    this.currentStatus = currentStatus;
    this.updatedOn = updatedOn;
  }
}