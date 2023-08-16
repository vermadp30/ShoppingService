/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.repository;

import com.stickyio.dao.CustomerOrderMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderMapping, Long> {

  CustomerOrderMapping findFirstByOrderId(Long orderId);

  @Modifying
  @Query("UPDATE CustomerOrderMapping co SET co.currentStatus = :newStatus WHERE co.orderId = :orderId")
  int updateCourierStatusByOrderId(@Param("orderId") Long orderId,
      @Param("newStatus") String newStatus);

  List<CustomerOrderMapping> findByCustomerId(Long customerId);
}
