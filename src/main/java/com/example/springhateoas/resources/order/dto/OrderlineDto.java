package com.example.springhateoas.resources.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.example.springhateoas.domain.order.model.Orderline;
import com.example.springhateoas.resources.order.OrderOrderlineResource;
import com.example.springhateoas.resources.order.OrderResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Relation(collectionRelation = "items")
public class OrderlineDto extends ResourceSupport {

    private Long orderlineId;
    private String productId;
    private Integer quantity;

    public OrderlineDto(Orderline orderline, long orderId) {
        this.orderlineId = orderline.getId();
        this.productId = orderline.getProductId();
        this.quantity = orderline.getQuantity();

        linkSelf(orderId);
        linkOrder(orderId);
    }

    private void linkSelf(long orderId) {
        this.add(ControllerLinkBuilder.linkTo(methodOn(OrderOrderlineResource.class).getOrderlineById(orderId, orderlineId)).withSelfRel());
    }

    private void linkOrder(long orderId) {
        this.add(linkTo(methodOn(OrderResource.class).getOrder(orderId)).withRel("order"));
    }

}
