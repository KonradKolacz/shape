package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnyChangeException extends RuntimeException {

    public String getMessage() {
        return "All attributes to change are the same as the old values";
    }
}
