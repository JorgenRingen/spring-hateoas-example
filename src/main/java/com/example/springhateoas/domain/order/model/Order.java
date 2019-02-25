package com.example.springhateoas.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.springhateoas.domain.order.Orderstatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public
class Order {

    @Id
    @GeneratedValue
    private Long id;

    private Orderstatus orderstatus;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orderline> orderlines = new ArrayList<>();

    public Optional<Orderline> findOrderlineWithId(long orderlineId) {
        return orderlines.stream()
                .filter(ol -> ol.getId() == orderlineId)
                .findFirst();
    }

    public boolean canSubmit() {
        return this.orderstatus.canSubmit();
    }

    public void submit() {
        this.orderstatus = Orderstatus.SUBMITTED;
    }

    public boolean canAppendOrderline() {
        return this.orderstatus.canAppendOrderline();
    }

    public void appendOrderline(Orderline orderline) {
        this.orderlines.add(orderline);
    }

    public boolean canCancel() {
        return this.orderstatus.canCancel();
    }

    public boolean isCanceled() {
        return this.getOrderstatus() == Orderstatus.CANCELED;
    }
}
