package com.example.final_test.changer;

import com.example.final_test.domain.Circle;
import com.example.final_test.domain.Shape;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CircleChanger implements Changer {
    private static final String TYPE = "CIRCLE";
    private static final String R = "r";
    private static final List<String> ATTRIBUTES = List.of(R);

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<String> getListOfAttributes() {
        return ATTRIBUTES;
    }

    @Override
    public BigDecimal changeValueAndGetOldIfNotEqualsNew(Shape shape, String attributeName, BigDecimal newValue) {
        Circle circle = (Circle) shape;
        BigDecimal oldValue = null;
        if (attributeName.equals(R) && !circle.getR().equals(newValue)) {
            oldValue = circle.getR();
            circle.setR(newValue);
        }
        return oldValue;
    }


}
