/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.orderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.stickyio.orderservice.dao.OrderStatus;

@Setter
@Getter
@NoArgsConstructor
public class OrderReplyDto {

    Long customerId;
    Long orderId;
    OrderStatus status;

    public OrderReplyDto(Long customerId, Long orderId, OrderStatus status) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.status = status;
    }
}
