package com.example.final_test.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RectangleDto extends ShapeDto {
    private BigDecimal a;
    private BigDecimal b;
}
