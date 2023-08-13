package com.stickyio.customerservice.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@Setter
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "email-id", unique = true, nullable = false)
    private String emailId;
}

