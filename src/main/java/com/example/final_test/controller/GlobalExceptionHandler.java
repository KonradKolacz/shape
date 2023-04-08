package com.example.final_test.controller;

import com.example.final_test.dto.ExceptionDto;
import com.example.final_test.dto.ValidationErrorDto;
import com.example.final_test.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler({NoShapeTypeException.class, BadNumberOfParametersExceptions.class,
            UserExistsException.class, BadAttributeNameException.class,
            NoRoleTypeException.class, AnyChangeException.class})
    public ResponseEntity<ExceptionDto> handleOtherRuntimeExceptions(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ObjectNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionDto> handleNotFoundException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> handleValidationErrors(MethodArgumentNotValidException e) {
        final List<ValidationErrorDto> errors = e.getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
