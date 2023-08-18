/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.repository;

import com.stickyio.dao.OrderTrackingData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTrackingDataRepository extends JpaRepository<OrderTrackingData, Long> {


  @Query("SELECT d FROM OrderTrackingData d WHERE d.orderId = :orderId ORDER BY d.createdOn DESC")
  Optional<OrderTrackingData> findLatestOrderTrackingDataByOrderId(@Param("orderId") Long orderId);
}
