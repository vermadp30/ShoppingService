/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.repository;

import com.stickyio.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
