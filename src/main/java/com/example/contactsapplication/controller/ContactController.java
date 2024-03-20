package com.example.contactsapplication.controller;

import com.example.contactsapplication.dto.in_out.AddressDetailsDto;
import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.service.AddressService;
import com.example.contactsapplication.service.ContactService;
import com.example.contactsapplication.service.PhoneNumberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@AllArgsConstructor
public class ContactController {

    ContactService contactService;
    PhoneNumberService phoneNumberService;
    AddressService addressService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ContactListItemDto>> getContactsByUserId(@PathVariable Long userId) {
        List<ContactListItemDto> results = contactService.getContactsByUserId(userId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContactsByUserId(@PathVariable Long contactId) {
        contactService.deleteContactById(contactId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addNewContact(@RequestBody ContactDetailsDto contactDetailsDto) {
        contactService.addNewContact(contactDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/phones/{contactId}")
    public ResponseEntity<List<PhoneDetailsDto>> getPhonesByContactId(@PathVariable Long contactId) {
        List<PhoneDetailsDto> phonesList = phoneNumberService.getPhonesByContactId(contactId);
        return new ResponseEntity<>(phonesList, HttpStatus.OK);
    }

    @GetMapping("/addresses/{contactId}")
    public ResponseEntity<List<AddressDetailsDto>> getAddressesByContactId(@PathVariable Long contactId) {
        List<AddressDetailsDto> addressesList = addressService.getAddressesByContactId(contactId);
        return new ResponseEntity<>(addressesList, HttpStatus.OK);
    }

    @DeleteMapping("/phones/{phoneId}")
    public ResponseEntity<Void> deletePhoneByPhoneId(@PathVariable Long phoneId) {
        phoneNumberService.deletePhoneById(phoneId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddressByAddressId(@PathVariable Long addressId) {
        addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
