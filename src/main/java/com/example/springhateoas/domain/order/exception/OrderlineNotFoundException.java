package com.example.springhateoas.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Orderline not found")
public class OrderlineNotFoundException extends RuntimeException {

    public OrderlineNotFoundException(long orderId, long orderlineId) {
        super("Orderline with id=" + orderlineId + " not found on order with id=" + orderId);
    }
}
