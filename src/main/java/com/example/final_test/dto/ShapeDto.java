package com.example.final_test.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShapeDto {
    private Long id;
    private Long version;
    private String type;
    private String createdBy;
    private LocalDate createdAt;
    private String lastModifiedBy;
    private LocalDate lastModifiedAt;
    private BigDecimal area;
    private BigDecimal perimeter;
}
