/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long customerId;

  private String Item;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private String statusDetail;

  private Date updatedOn;

  public Order(Long customerId, OrderStatus status, String statusDetail, Date updatedOn) {
    this.customerId = customerId;
    this.status = status;
    this.statusDetail = statusDetail;
    this.updatedOn = updatedOn;
  }
}