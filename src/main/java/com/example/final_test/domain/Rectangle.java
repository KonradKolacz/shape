package com.example.final_test.domain;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("RECTANGLE")
public class Rectangle extends Shape {

    private BigDecimal a;
    private BigDecimal b;

    @Override
    public BigDecimal getArea() {
        return a.multiply(b);
    }

    @Override
    public BigDecimal getPerimeter() {
        return a.multiply(BigDecimal.valueOf(2)).add(b.multiply(BigDecimal.valueOf(2)));
    }
}
