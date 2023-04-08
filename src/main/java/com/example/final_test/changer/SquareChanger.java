package com.example.final_test.changer;

import com.example.final_test.domain.Shape;
import com.example.final_test.domain.Square;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SquareChanger implements Changer {
    private static final String TYPE = "SQUARE";
    private static final String A = "a";
    private static final List<String> ATTRIBUTES = List.of(A);

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
        Square square = (Square) shape;
        BigDecimal oldValue = null;
        if (attributeName.equals(A) && !square.getA().equals(newValue)) {
            oldValue = square.getA();
            square.setA(newValue);
        }
        return oldValue;
    }
}
