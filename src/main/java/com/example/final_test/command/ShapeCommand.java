package com.example.final_test.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShapeCommand {
    @NotBlank
    private String type;
    @NotEmpty
    private List<@Positive BigDecimal> parameters;
}
