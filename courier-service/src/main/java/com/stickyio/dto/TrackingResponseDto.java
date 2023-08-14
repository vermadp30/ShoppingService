/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResponseDto {
    private Long orderId;
    private String currentStatus;
    private Boolean isDelivered;

    @Override
    public String toString() {
        return "TrackingResponseDto{" +
                "orderId=" + orderId +
                ", currentStatus='" + currentStatus + '\'' +
                ", isDelivered=" + isDelivered +
                '}';
    }
}
