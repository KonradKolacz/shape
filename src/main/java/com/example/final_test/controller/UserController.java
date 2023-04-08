package com.example.final_test.controller;

import com.example.final_test.command.UserCommand;
import com.example.final_test.dto.UserDto;
import com.example.final_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody @Valid UserCommand userCommand) {
        return new ResponseEntity<>(userService.add(userCommand), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        return new ResponseEntity<>(userService.findAllUsers(pageable), HttpStatus.OK);
    }
}
