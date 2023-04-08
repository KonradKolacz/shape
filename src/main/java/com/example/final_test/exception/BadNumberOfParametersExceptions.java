package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BadNumberOfParametersExceptions extends RuntimeException {
    private String type;
    private int numberOfParameters;

    public String getMessage() {
        return "ShapeType: " + type + " should have " + numberOfParameters + " parameter(s)";
    }
}
