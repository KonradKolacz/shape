package com.example.final_test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectNotFoundException extends RuntimeException{
    private long id;
    private String name;

    public String getMessage() {
        return name + " with id: " + id + " not found";
    }
}
