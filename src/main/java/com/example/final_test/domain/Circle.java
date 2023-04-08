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
@DiscriminatorValue("CIRCLE")
public class Circle extends Shape {
    private BigDecimal r;

    @Override
    public BigDecimal getArea() {
        return BigDecimal.valueOf(Math.PI).multiply(r).multiply(r);
    }

    @Override
    public BigDecimal getPerimeter() {
        return BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(Math.PI)).multiply(r);
    }
}
