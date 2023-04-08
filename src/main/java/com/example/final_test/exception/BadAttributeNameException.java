package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BadAttributeNameException extends RuntimeException{
    private String type;
    private String attributeName;

    public String getMessage() {
        return "ShapeType: " + type + " do not contain attribute " + attributeName ;
    }
}
