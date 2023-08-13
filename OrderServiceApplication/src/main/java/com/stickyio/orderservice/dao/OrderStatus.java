package com.stickyio.orderservice.dao;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER_PLACED,
    ORDER_ON_THE_WAY,
    ORDER_DELIVERED
}
