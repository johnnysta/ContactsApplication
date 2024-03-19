package com.example.contactsapplication.dto.in;

import lombok.Data;


@Data
public class UserCreationDto {

    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
