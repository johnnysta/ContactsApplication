package com.example.contactsapplication.controller;

import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@AllArgsConstructor
public class ContactController {

    ContactService contactService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ContactListItemDto>> getContactsByUserId(@PathVariable Long userId) {
        List<ContactListItemDto> results = contactService.getContactsByUserId(userId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }



}
