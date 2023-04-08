package com.example.final_test.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AttributeChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String attributeName;
    private BigDecimal oldValue;
    private BigDecimal newValue;
    @ManyToOne
    private Change change;

    public AttributeChange(String attributeName, BigDecimal oldValue, BigDecimal newValue) {
        this.attributeName = attributeName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
