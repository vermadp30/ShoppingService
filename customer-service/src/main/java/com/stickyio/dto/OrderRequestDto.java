/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.dto;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {

  private Long customerId;
  private String item;
  private Date createdOn;

  public OrderRequestDto(Long customerId, String item, Date date) {
    this.customerId = customerId;
    this.item = item;
    this.createdOn = date;
  }

  @Override
  public String toString() {
    return "OrderRequestDto{" +
        "customerId=" + customerId +
        ", item='" + item + '\'' +
        ", createdOn=" + createdOn +
        '}';
  }
}
