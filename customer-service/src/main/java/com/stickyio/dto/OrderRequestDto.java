/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
    private Long customerId;
    private String item;

    public OrderRequestDto(Long customerId, String item) {
        this.customerId = customerId;
        this.item = item;
    }

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "customerId=" + customerId +
                ", item='" + item + '\'' +
                '}';
    }
}
