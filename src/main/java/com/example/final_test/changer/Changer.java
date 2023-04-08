package com.example.final_test.changer;

import com.example.final_test.domain.AttributeChange;
import com.example.final_test.domain.Shape;
import com.example.final_test.exception.BadAttributeNameException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public interface Changer {
    String getType();

    List<String> getListOfAttributes();

    BigDecimal changeValueAndGetOldIfNotEqualsNew(Shape shape, String attributeName, BigDecimal newValue);

    default AttributeChange makeAttributeChange(Shape shape, String attributeName, BigDecimal newValue) {
        BigDecimal oldValue = changeValueAndGetOldIfNotEqualsNew(shape, attributeName, newValue.setScale(2, RoundingMode.HALF_UP));
        if (oldValue == null) {
            return null;
        }
        return new AttributeChange(attributeName, oldValue, newValue);
    }

    default void validAttributeToUpdate(String attributeName) {
        if (!getListOfAttributes().contains(attributeName)) {
            throw new BadAttributeNameException(getType(), attributeName);
        }
    }

}
