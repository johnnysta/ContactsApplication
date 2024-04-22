package com.example.contactsapplication.controller;

import com.example.contactsapplication.dto.in_out.AddressDetailsDto;
import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneRegistrationInitDataDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.service.AddressService;
import com.example.contactsapplication.service.ContactService;
import com.example.contactsapplication.service.PhoneNumberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@AllArgsConstructor
@Slf4j
public class ContactController {

    ContactService contactService;
    PhoneNumberService phoneNumberService;
    AddressService addressService;


    //This endpoint is just for testing security
    @PreAuthorize("hasAuthority('NEW_USER')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Security";
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<ContactListItemDto>> getContactsByUserId(@PathVariable Long userId) {
        List<ContactListItemDto> results = contactService.getContactsByUserId(userId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/contact/{contactId}")
    public ResponseEntity<ContactDetailsDto> getContactById(@PathVariable Long contactId) {
        log.info("contactId" + contactId);
        ContactDetailsDto result = contactService.getContactById(contactId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContactsByUserId(@PathVariable Long contactId) {
        contactService.deleteContactById(contactId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Long> addNewContact(@RequestBody ContactDetailsDto contactDetailsDto) {
        Long newContactId = contactService.addNewContact(contactDetailsDto);
        return new ResponseEntity<>(newContactId, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/phones/{contactId}")
    public ResponseEntity<List<PhoneDetailsDto>> getPhonesByContactId(@PathVariable Long contactId) {
        List<PhoneDetailsDto> phonesList = phoneNumberService.getPhonesByContactId(contactId);
        return new ResponseEntity<>(phonesList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/addresses/{contactId}")
    public ResponseEntity<List<AddressDetailsDto>> getAddressesByContactId(@PathVariable Long contactId) {
        List<AddressDetailsDto> addressesList = addressService.getAddressesByContactId(contactId);
        return new ResponseEntity<>(addressesList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @DeleteMapping("/phones/{phoneId}")
    public ResponseEntity<Void> deletePhoneByPhoneId(@PathVariable Long phoneId) {
        phoneNumberService.deletePhoneById(phoneId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddressByAddressId(@PathVariable Long addressId) {
        addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/phones")
    public ResponseEntity<Void> addNewPhone(@RequestBody PhoneDetailsDto phoneDetailsDto) {
        phoneNumberService.addNewPhone(phoneDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/addresses")
    public ResponseEntity<Void> addNewAddress(@RequestBody AddressDetailsDto addressDetailsDto) {
        addressService.addNewAddress(addressDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/phones/init-data")
    public ResponseEntity<PhoneRegistrationInitDataDto> getPhoneRegistrationInitData() {
        return new ResponseEntity<>(phoneNumberService.getPhoneRegistrationInitData(), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PutMapping("{contactId}")
    public ResponseEntity<Void> updateContactById(@PathVariable Long contactId, @RequestBody ContactDetailsDto contactDetailsDto) {
        contactService.updateContactById(contactId, contactDetailsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/phones/phones-list/{contactId}")
    public ResponseEntity<Void> replacePhonesList(@PathVariable Long contactId, @RequestBody List<PhoneDetailsDto> phoneDetailsDtos) {
        phoneNumberService.replacePhonesList(contactId, phoneDetailsDtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/addresses/addresses-list/{contactId}")
    public ResponseEntity<Void> replaceAddressesList(@PathVariable Long contactId, @RequestBody List<AddressDetailsDto> addressDetailsDtos) {
        addressService.replaceAddressesList(contactId, addressDetailsDtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
