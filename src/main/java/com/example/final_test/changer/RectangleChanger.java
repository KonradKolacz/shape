package com.example.final_test.changer;

import com.example.final_test.domain.Rectangle;
import com.example.final_test.domain.Shape;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RectangleChanger implements Changer {
    private static final String TYPE = "RECTANGLE";
    private static final String A = "a";
    private static final String B = "b";
    private static final List<String> ATTRIBUTES = List.of(A, B);

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
        Rectangle rectangle = (Rectangle) shape;
        BigDecimal oldValue = null;
        if (attributeName.equals(A) && !rectangle.getA().equals(newValue)) {
            oldValue = rectangle.getA();
            rectangle.setA(newValue);
        }
        if (attributeName.equals(B) && !rectangle.getB().equals(newValue)) {
            oldValue = rectangle.getB();
            rectangle.setB(newValue);
        }
        return oldValue;
    }

}
