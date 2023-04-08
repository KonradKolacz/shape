package com.example.final_test.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeChangeDto {
    private String attributeName;
    private BigDecimal oldValue;
    private BigDecimal newValue;
}
