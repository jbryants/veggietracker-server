package com.example.veggietracker.dto;

import com.example.veggietracker.model.Quantity;
import com.example.veggietracker.model.Unit;

import java.math.BigDecimal;

public class ListItemCreateRequest {
    private BigDecimal basePrice;
    private Quantity baseQuantity;
    private Unit baseUnit;
    private Long itemId;
    private Long listId;
    private BigDecimal totalQuantity;
    private Unit unit;

    public ListItemCreateRequest(BigDecimal basePrice, Quantity baseQuantity, Unit baseUnit, Long itemId, Long listId,
                                 BigDecimal totalQuantity, Unit unit) {
        this.basePrice = basePrice;
        this.baseQuantity = baseQuantity;
        this.baseUnit = baseUnit;
        this.itemId = itemId;
        this.listId = listId;
        this.totalQuantity = totalQuantity;
        this.unit = unit;
    }

    public ListItemCreateRequest() {
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Quantity getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(Quantity baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public Unit getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(Unit baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "CreateListItemRequest [basePrice=" + basePrice + ", baseQuantity=" + baseQuantity + ", baseUnit="
                + baseUnit + ", itemId=" + itemId + ", listId=" + listId + ", totalQuantity=" + totalQuantity
                + ", unit=" + unit + "]";
    }
}

