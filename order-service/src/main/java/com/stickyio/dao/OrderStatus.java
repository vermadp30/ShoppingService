/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dao;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER_PLACED,
    ORDER_ON_THE_WAY,
    ORDER_DELIVERED
}
