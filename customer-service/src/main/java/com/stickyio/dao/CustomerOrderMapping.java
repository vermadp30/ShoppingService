/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dao;

import jakarta.persistence.*;

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

    @Column(name= "order-id", nullable = false, unique = true)
    private Long orderId;

    private String currentStatus;

    public CustomerOrderMapping(Long customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}