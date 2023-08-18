/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.repository;

import com.stickyio.dao.Order;
import com.stickyio.dao.OrderStatus;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Modifying
  @Query("UPDATE Order o SET o.status = :newStatus, o.statusDetail = :statusDetail, o.updatedOn = :updatedOn  WHERE o.id = :orderId")
  void updateOrderStatusAndStatusDetailByOrderId(@Param("orderId") Long orderId,
      @Param("newStatus") OrderStatus newStatus,
      @Param("statusDetail") String statusDetail,
      @Param("updatedOn") Date updatedOn);
}
