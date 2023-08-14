/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="couriers")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long orderId;

    private String currentStatus;

    private Boolean isDelivered;

    public Courier(Long orderId, String currentStatus, Boolean isDelivered) {
        this.orderId = orderId;
        this.currentStatus = currentStatus;
        this.isDelivered = isDelivered;
    }
}
