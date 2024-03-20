package com.example.contactsapplication.dto.out;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticatedUserDto {
    private Long userId;
    private String username;
    private List<String> roles;
    private String email;
}
