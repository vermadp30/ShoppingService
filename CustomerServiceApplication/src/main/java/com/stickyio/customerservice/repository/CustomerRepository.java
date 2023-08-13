package com.stickyio.customerservice.repository;

import com.stickyio.customerservice.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer getByEmailId(String emailId);

}
