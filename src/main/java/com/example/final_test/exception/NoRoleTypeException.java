package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoRoleTypeException extends RuntimeException{
    private String role;

    public String getMessage() {
        return "Role: " + role + " is not supported";
    }
}
