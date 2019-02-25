package com.example.springhateoas.domain.order;

public enum Orderstatus {

    CREATED,
    SUBMITTED,
    CANCELED;

    public boolean canSubmit() {
        return this != CANCELED && this != SUBMITTED;
    }

    public boolean canAppendOrderline() {
        return this == CREATED;
    }

    public boolean canCancel() {
        return this == CREATED;
    }
}
