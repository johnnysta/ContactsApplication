package com.example.contactsapplication.dto.out;

import lombok.Data;

@Data
public class AuthenticatedUserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
