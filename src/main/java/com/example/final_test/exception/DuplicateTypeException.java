package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateTypeException extends RuntimeException{
    private String classOne;
    private String classTwo;

    public String getMessage() {
        return "Found duplicated producers or changers assigned to one shape type value: " + classOne + " " + classTwo;
    }
}
