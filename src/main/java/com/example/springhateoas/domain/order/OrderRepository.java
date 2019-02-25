package com.example.springhateoas.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springhateoas.domain.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
