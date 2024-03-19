package com.example.contactsapplication.controller;


import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
