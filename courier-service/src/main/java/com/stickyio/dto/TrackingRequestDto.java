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
public class TrackingRequestDto {
    private Long orderId;

    @Override
    public String toString() {
        return "TrackingRequestDto{" +
                "orderId=" + orderId +
                '}';
    }
}
