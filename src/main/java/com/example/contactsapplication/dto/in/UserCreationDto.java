package com.example.contactsapplication.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserCreationDto {


    @NotNull(message = "Role cannot be empty!")
    private String role;


    @NotNull(message = "First name cannot be empty!")
    @Size(min = 3, message = "First name must be at least 3 characters long!")
    private String firstName;


    @NotNull(message = "Last name cannot be empty!")
    @Size(min = 3, message = "Last name must be at least 3 characters long!")
    private String lastName;


    @NotNull(message = "E-mail cannot be empty!")
    @Email(message = "Please provide a valid email address.")
    @Size(min = 3, message = "E-mail must be at least 3 characters long!")
    private String email;


    @NotNull(message = "Password cannot be empty!")
    @Size(min = 3, message = "Last name must be at least 3 characters long!")
    private String password;
}
