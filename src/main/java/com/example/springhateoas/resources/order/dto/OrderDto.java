package com.example.springhateoas.resources.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.example.springhateoas.domain.order.Orderstatus;
import com.example.springhateoas.domain.order.model.Order;
import com.example.springhateoas.resources.order.OrderOrderlineResource;
import com.example.springhateoas.resources.order.OrderResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
@NoArgsConstructor
@Relation(collectionRelation = "items")
public class OrderDto extends ResourceSupport {

    private Long orderId;
    private Orderstatus orderstatus;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.orderstatus = order.getOrderstatus();
        linkSelf();
        linkOrderlines();

        if (order.canSubmit()) {
            linkSubmit();
        }
    }

    private void linkSelf() {
        this.add(ControllerLinkBuilder.linkTo(methodOn(OrderResource.class).getOrder(this.orderId)).withSelfRel());
    }

    private void linkOrderlines() {
        this.add(linkTo(methodOn(OrderOrderlineResource.class).getOrderlines(this.orderId)).withRel("orderlines"));
    }

    private void linkSubmit() {
        this.add(linkTo(methodOn(OrderResource.class).postSubmitOrder(this.orderId)).withRel("submit"));
    }
}
