package com.example.contactsapplication.controller;


import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.dto.out.AuthenticatedUserDto;
import com.example.contactsapplication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserEntityController {

    UserService userService;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserCreationDto userCreationDto) {
        userService.addUser(userCreationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUserDto> getUserInfo(Principal principal) {
        AuthenticatedUserDto authenticatedUserDto = null;
        if (principal != null) {
//            authenticatedUserDto = userService.mapPrincipalToUserDetails(principal);
        } else {
            // TODO Impl
        }
        return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
    }

}
