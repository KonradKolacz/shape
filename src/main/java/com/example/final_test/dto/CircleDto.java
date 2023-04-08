package com.example.final_test.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CircleDto extends ShapeDto {
    private BigDecimal r;
}
