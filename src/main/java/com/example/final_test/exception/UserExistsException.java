package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserExistsException extends RuntimeException {
    private String username;

    public String getMessage() {
        return "User with username: " + username + " exists.";
    }
}
