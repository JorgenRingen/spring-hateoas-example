package com.example.springhateoas.resources.order;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.springhateoas.domain.order.OrderService;
import com.example.springhateoas.domain.order.exception.OrderlineNotFoundException;
import com.example.springhateoas.domain.order.model.Order;
import com.example.springhateoas.domain.order.model.Orderline;
import com.example.springhateoas.resources.order.dto.OrderlineDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("orders/{orderId}/orderlines")
public class OrderOrderlineResource {

    private final OrderService orderService;

    public OrderOrderlineResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity getOrderlines(@PathVariable long orderId) {
        Order order = orderService.getOrderById(orderId);

        List<OrderlineDto> orderlineDtos = order.getOrderlines().stream()
                .map(orderline -> new OrderlineDto(orderline, orderId))
                .collect(Collectors.toList());

        Link self = linkTo(methodOn(OrderOrderlineResource.class).getOrderlines(orderId)).withSelfRel();

        return ResponseEntity.ok(new Resources<>(orderlineDtos, self));
    }

    @GetMapping("{orderlineId}")
    public ResponseEntity getOrderlineById(@PathVariable long orderId, @PathVariable long orderlineId) {
        Order order = orderService.getOrderById(orderId);
        Orderline orderline = order.findOrderlineWithId(orderlineId)
                .orElseThrow(() -> new OrderlineNotFoundException(orderId, orderlineId));

        OrderlineDto orderlineDto = new OrderlineDto(orderline, orderId);

        return ResponseEntity.ok(new Resource<>(orderlineDto));
    }

    @PostMapping
    public ResponseEntity postOrderline(@PathVariable long orderId, @RequestBody OrderlineDto orderlineDto) {
        Orderline orderline = orderService.appendOrderlineToOrder(orderId, orderlineDto);

        URI createdUri = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment(orderline.getId().toString()).build().toUri();

        return ResponseEntity.created(createdUri).build();
    }
}
