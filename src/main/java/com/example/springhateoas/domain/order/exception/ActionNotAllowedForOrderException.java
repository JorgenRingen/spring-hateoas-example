package com.example.springhateoas.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.springhateoas.domain.order.model.Order;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class ActionNotAllowedForOrderException extends RuntimeException {

    private ActionNotAllowedForOrderException(String reason) {
        super(reason);
    }

    public static ActionNotAllowedForOrderException submitNotAllowed(Order order) {
        String reason = String.format("Order with id=%s and status=%s can't be submitted", order.getId(), order.getOrderstatus());
        return new ActionNotAllowedForOrderException(reason);
    }

    public static ActionNotAllowedForOrderException appendOrderlineNotAllowed(Order order) {
        String reason = String.format("Can't append orderline to order with id=%s and status=%s", order.getId(), order.getOrderstatus());
        return new ActionNotAllowedForOrderException(reason);
    }
}
