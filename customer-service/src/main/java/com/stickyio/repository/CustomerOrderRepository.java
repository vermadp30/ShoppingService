/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.repository;

import com.stickyio.dao.CustomerOrderMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderMapping,Long> {
    Optional<String> getCurrentStatusByOrderId(Long orderId);
    @Modifying
    @Query("UPDATE Courier c SET c.currentStatus = :newStatus WHERE c.orderId = :orderId")
    void updateCourierStatusByOrderId(@Param("orderId") Long orderId,
                                      @Param("newStatus") String newStatus);
}
