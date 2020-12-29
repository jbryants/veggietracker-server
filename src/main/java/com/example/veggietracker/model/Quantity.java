package com.example.veggietracker.model;
import java.math.BigDecimal;

public enum Quantity {
    QUARTER(new BigDecimal("0.250")), WHOLE(new BigDecimal("1.000"));

    private final BigDecimal quantity;

    private Quantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public static Quantity fromQuantity(BigDecimal dbData) {
        switch (dbData.toString()) {
            case "0.250":
                return Quantity.QUARTER;
            case "1.000":
                return Quantity.WHOLE;
            default:
                throw new IllegalArgumentException("Quantity [" + dbData + "] not supported.");
        }
    }
}

