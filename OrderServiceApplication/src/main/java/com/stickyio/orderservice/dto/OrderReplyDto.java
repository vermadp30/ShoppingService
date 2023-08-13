package com.stickyio.orderservice.dto;

import com.stickyio.orderservice.dao.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
