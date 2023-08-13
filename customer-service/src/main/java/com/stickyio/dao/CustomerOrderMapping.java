/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dao;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer-order-mapping")
@NoArgsConstructor
@Setter
@Getter
public class CustomerOrderMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "customer-id", nullable = false)
    Long customerId;

    @Column(name= "order-id", nullable = false, unique = true)
    Long orderId;

    public CustomerOrderMapping(Long customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }
}