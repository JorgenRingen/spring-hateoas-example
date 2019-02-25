package com.example.springhateoas.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springhateoas.domain.order.model.Orderline;

interface OrderlineRepository extends JpaRepository<Orderline, Long> {

}
