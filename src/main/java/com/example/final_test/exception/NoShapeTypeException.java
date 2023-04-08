package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoShapeTypeException extends RuntimeException{
    private String type;

    public String getMessage() {
        return "ShapeType: " + type + " is not supported";
    }
}
