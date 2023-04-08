package com.example.final_test.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("SQUARE")
public class Square extends Shape {
    private BigDecimal a;

    @Override
    public BigDecimal getArea() {
        return a.multiply(a);
    }

    @Override
    public BigDecimal getPerimeter() {
        return BigDecimal.valueOf(4).multiply(a);
    }
}
