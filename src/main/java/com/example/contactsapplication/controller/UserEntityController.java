package com.example.contactsapplication.controller;


import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.dto.out.AuthenticatedUserDto;
import com.example.contactsapplication.mapper.UserMapper;
import com.example.contactsapplication.service.CustomUserDetails;
import com.example.contactsapplication.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserEntityController {

    UserService userService;
    UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserCreationDto userCreationDto) {
        userService.addUser(userCreationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUserDto> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails loggedInUserDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Queried user info in login: " + loggedInUserDetails.getEmail());

        AuthenticatedUserDto authenticatedUserDto = null;
        if (loggedInUserDetails != null) {
            authenticatedUserDto = userMapper.mapCustomUserDetailsToAuthenticatedUserDto(loggedInUserDetails);
        } else {
            log.info("Unauthorized during logib");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
    }


    @GetMapping("/userInfo")
    public ResponseEntity<AuthenticatedUserDto> getUserInfo(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AuthenticatedUserDto authenticatedUserDto = null;
        if (principal != null) {
            CustomUserDetails loggedInUserDetails = (CustomUserDetails) authentication.getPrincipal();
            log.info("Queried user info: " + loggedInUserDetails.getEmail());
            authenticatedUserDto = userMapper.mapCustomUserDetailsToAuthenticatedUserDto(loggedInUserDetails);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
    }

}
