package com.example.products.entity;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonValue;

@Getter
public enum Status {
    AVAILABLE("Available"),
    UNAVAILABLE("Out of stock");

    private final String displayStatus;

    Status(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    @JsonValue
    public String getDisplayStatus() {
        return displayStatus;
    }
}
