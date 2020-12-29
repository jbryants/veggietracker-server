package com.example.veggietracker.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.example.veggietracker.model.Quantity;
import org.openapitools.jackson.nullable.JsonNullable;


//@SuppressWarnings("FieldMayBeFinal")  TODO: REMOVE COMMENT
public class ListItemUpdateRequest {

    @NotNull
    private JsonNullable<BigDecimal> totalQuantity = JsonNullable.undefined();

    @NotNull
    private JsonNullable<Quantity> baseQuantity = JsonNullable.undefined();

    @NotNull
    private JsonNullable<BigDecimal> basePrice = JsonNullable.undefined();

    public ListItemUpdateRequest() {}

    public JsonNullable<BigDecimal> getTotalQuantity() {
        return totalQuantity;
    }

    public JsonNullable<Quantity> getBaseQuantity() {
        return baseQuantity;
    }

    public JsonNullable<BigDecimal> getBasePrice() {
        return basePrice;
    }
}
