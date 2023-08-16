/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.repository;

import com.stickyio.dao.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findCourierByOrderId(Long orderId);

    @Modifying
    @Query("UPDATE Courier c SET c.currentStatus = :newStatus, c.isDelivered = :newIsDelivered WHERE c.orderId = :orderId")
    void updateCourierStatusAndIsDeliveredByOrderId(@Param("orderId") Long orderId,
                                                    @Param("newStatus") String newStatus,
                                                    @Param("newIsDelivered") boolean newIsDelivered);
}
