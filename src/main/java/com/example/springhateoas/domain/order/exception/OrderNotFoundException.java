package com.example.springhateoas.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order not found")
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(long id) {
        super("Order with order id=" + id + " not found");
    }
}
