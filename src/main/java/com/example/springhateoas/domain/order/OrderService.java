package com.example.springhateoas.domain.order;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springhateoas.domain.order.exception.ActionNotAllowedForOrderException;
import com.example.springhateoas.domain.order.exception.OrderNotFoundException;
import com.example.springhateoas.domain.order.model.Order;
import com.example.springhateoas.domain.order.model.Orderline;
import com.example.springhateoas.resources.order.dto.OrderlineDto;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderlineRepository orderlineRepository;

    public OrderService(OrderRepository orderRepository, OrderlineRepository orderlineRepository) {
        this.orderRepository = orderRepository;
        this.orderlineRepository = orderlineRepository;
    }

    public List<Order> findAll(Example<Order> orderProbe) {
        return orderRepository.findAll(orderProbe);
    }

    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Order create() {
        Order order = Order.builder()
                .orderstatus(Orderstatus.CREATED)
                .build();

        return orderRepository.save(order);
    }

    public Order cancel(long orderId) {
        Order order = getOrderById(orderId);
        if (order.isCanceled()) {
            return order;
        }

        order.setOrderstatus(Orderstatus.CANCELED);
        return order;
    }

    public Order submit(long orderId) {
        Order order = getOrderById(orderId);
        if (!order.canSubmit()) {
            throw ActionNotAllowedForOrderException.submitNotAllowed(order);
        }

        order.submit();

        return order;
    }

    public Orderline appendOrderlineToOrder(long orderId, OrderlineDto orderlineDto) {
        Order order = getOrderById(orderId);
        if (!order.canAppendOrderline()) {
            throw ActionNotAllowedForOrderException.appendOrderlineNotAllowed(order);
        }

        Orderline orderline = Orderline.builder()
                .productId(orderlineDto.getProductId())
                .quantity(orderlineDto.getQuantity())
                .build();

        Orderline savedOrderline = orderlineRepository.save(orderline);
        order.appendOrderline(savedOrderline);

        return savedOrderline;
    }
}
