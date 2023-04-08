package com.example.final_test.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Map;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShapeUpdateCommand {
    private Map<@NotBlank String, @Positive BigDecimal> valueToUpdate;
}
