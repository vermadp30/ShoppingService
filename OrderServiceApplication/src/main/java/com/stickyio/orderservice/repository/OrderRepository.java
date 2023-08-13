/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stickyio.orderservice.dao.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
