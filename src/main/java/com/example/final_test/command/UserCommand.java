package com.example.final_test.command;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCommand {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String role;

}
