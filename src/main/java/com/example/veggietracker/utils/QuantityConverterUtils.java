package com.example.veggietracker.utils;

import com.example.veggietracker.model.Quantity;

import java.math.BigDecimal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuantityConverterUtils implements AttributeConverter<Quantity, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Quantity quantity) {
        return quantity.getQuantity();
    }

    @Override
    public Quantity convertToEntityAttribute(BigDecimal dbData) {
        return Quantity.fromQuantity(dbData);
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                