package com.example.springhateoas.resources.order;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.springhateoas.domain.order.OrderService;
import com.example.springhateoas.domain.order.Orderstatus;
import com.example.springhateoas.domain.order.model.Order;
import com.example.springhateoas.resources.order.dto.OrderDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("orders")
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity find(@RequestParam(required = false) Orderstatus orderstatus) {
        Order orderProbe = Order.builder()
                .orderstatus(orderstatus)
                .build();

        List<OrderDto> orderDtos = orderService.findAll(Example.of(orderProbe)).stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(orderDtos.size(), 1, orderDtos.size());
        Resources<Resource<OrderDto>> resource = PagedResources.wrap(orderDtos, pageMetadata);
        resource.add(linkTo(methodOn(OrderResource.class).find(orderstatus)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<Resource<OrderDto>> getOrder(@PathVariable long orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderDto orderDto = new OrderDto(order);

        return ResponseEntity.ok(new Resource<>(orderDto));
    }

    @PostMapping
    public ResponseEntity postOrder() {
        Order order = orderService.create();
        URI createdUri = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment(order.getId().toString()).build().toUri();

        return ResponseEntity.created(createdUri).build();
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity deleteOrder(@PathVariable long orderId) {
        Order order = orderService.cancel(orderId);
        OrderDto orderDto = new OrderDto(order);

        return ResponseEntity.ok(new Resource<>(orderDto));
    }

    @PostMapping("{orderId}/submit")
    public ResponseEntity postSubmitOrder(@PathVariable long orderId) {
        Order order = orderService.submit(orderId);
        OrderDto submittedOrderDto = new OrderDto(order);

        return ResponseEntity.ok(new Resource<>(submittedOrderDto));
    }
}
