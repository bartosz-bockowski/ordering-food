package com.example.orderingfood.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserCommand {

    @NotEmpty(message = "username cannot be empty")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    private String password;

}
