package com.stickyio.customerservice.repository;

import com.stickyio.customerservice.dao.CustomerOrderMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderMapping,Long> {
}
