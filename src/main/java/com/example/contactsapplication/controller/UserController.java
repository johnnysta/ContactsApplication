package com.example.contactsapplication.controller;


import com.example.contactsapplication.dto.in.UserChangePasswordDto;
import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.dto.out.AuthenticatedUserDto;
import com.example.contactsapplication.mapper.UserMapper;
import com.example.contactsapplication.service.CustomUserDetails;
import com.example.contactsapplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    UserService userService;
    UserMapper userMapper;

    @Operation(summary = "Add new user", description = "A user logged in with admin role can add new users to the database.")
    @SecurityRequirement(name = "basicScheme", scopes = {"ADMIN"})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserCreationDto userCreationDto) {
        userService.addUser(userCreationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "User login", description = "Endpoint for user login. Login credentials arrive in Authorization header.")
    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUserDto> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails loggedInUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("Queried user info in login: " + loggedInUserDetails.getEmail());
        AuthenticatedUserDto authenticatedUserDto;
        if (loggedInUserDetails != null) {
            authenticatedUserDto = userMapper.mapCustomUserDetailsToAuthenticatedUserDto(loggedInUserDetails);
        } else {
            log.info("Unauthorized during login");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
    }


    @Operation(summary = "Change Password", description = "Endpoint for change password of the logged in user.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN", "NEW_USER", "NEW_ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN') || hasAuthority('NEW_USER') || hasAuthority('NEW_ADMIN')")
    @PostMapping("/changePw")
    public ResponseEntity<Void> changePassword(@RequestBody UserChangePasswordDto userChangePasswordDto, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            CustomUserDetails loggedInUserDetails = (CustomUserDetails) authentication.getPrincipal();
            userService.changePassword(userChangePasswordDto, loggedInUserDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new AccessDeniedException("You are not authenticated.");
        }
    }


    @Operation(summary = "Get logged in user info", description = "Endpoint for query the details of the logged in user.")
    @GetMapping("/userInfo")
    public ResponseEntity<AuthenticatedUserDto> getUserInfo(Principal principal) {
        AuthenticatedUserDto authenticatedUserDto;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            CustomUserDetails loggedInUserDetails = (CustomUserDetails) authentication.getPrincipal();
            log.info("Queried user info: " + loggedInUserDetails.getEmail());
            authenticatedUserDto = userMapper.mapCustomUserDetailsToAuthenticatedUserDto(loggedInUserDetails);
            return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

}
