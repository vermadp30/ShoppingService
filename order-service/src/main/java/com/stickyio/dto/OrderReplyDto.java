/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dto;

import com.stickyio.dao.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderReplyDto {

    private Long customerId;
    private Long orderId;
    private OrderStatus status;

    public OrderReplyDto(Long customerId, Long orderId, OrderStatus status) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.status = status;
    }
}
