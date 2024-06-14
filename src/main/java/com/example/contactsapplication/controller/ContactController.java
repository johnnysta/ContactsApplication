package com.example.contactsapplication.controller;

import com.example.contactsapplication.dto.in_out.*;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.service.AddressService;
import com.example.contactsapplication.service.ContactService;
import com.example.contactsapplication.service.PhoneNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Get contacts basic data", description = "This endpoint is just for testing.")
    @GetMapping("/hallo")
    public String hallo() {
        return "Hallo Hallo!";
    }

    //This endpoint is just for testing security
    @Operation(summary = "Get contacts basic data", description = "This endpoint is just for testing Spring Security.")
    @SecurityRequirement(name = "basicScheme", scopes = {"NEW_USER"})
    @PreAuthorize("hasAuthority('NEW_USER')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Security";
    }

    @Operation(summary = "Get contacts basic data", description = "Endpoint for getting basic data of contacts of a given user.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<ContactListItemDto>> getContactsByUserId(@PathVariable Long userId) {
        List<ContactListItemDto> results = contactService.getContactsByUserId(userId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Operation(summary = "Get one contact's basic data", description = "Endpoint for getting basic data of a contact by contactId.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/contactDetails/{contactId}")
    public ResponseEntity<ContactDetailsDto> getContactById(@PathVariable Long contactId) {
        log.info("contactId" + contactId);
        ContactDetailsDto result = contactService.getContactById(contactId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get one contact's full data", description = "Endpoint for getting full data" +
            " (including phones list and addresses list) of a contact by contactId.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/contact/{contactId}")
    public ResponseEntity<ContactFullDataDto> getFullContactById(@PathVariable Long contactId) {
        log.info("contactId" + contactId);
        ContactFullDataDto result = contactService.getFullContactById(contactId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> deleteContactsByUserId(@PathVariable Long contactId) {
        contactService.deleteContactById(contactId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Ads one contact's with basic data", description = "Endpoint for creating a new contact and adding basic data" +
            " (with empty phones list and addresses list) to it.")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/contactDetails/")
    public ResponseEntity<Long> addNewContact(@RequestBody ContactDetailsDto contactDetailsDto) {
        Long newContactId = contactService.addNewContact(contactDetailsDto).getId();
        return new ResponseEntity<>(newContactId, HttpStatus.CREATED);
    }


    @Operation(summary = "Ads one contact's with full data", description = "Endpoint  for creating a new contact, and adding full data" +
            " (including phones list and addresses list) to it.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/contact/")
    public ResponseEntity<Long> addNewFullContact(@RequestBody ContactFullDataDto contactFullDataDto) {
        Long newContactId = contactService.addNewFullContact(contactFullDataDto);
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
        phoneNumberService.addNewPhone(phoneDetailsDto, contactService.findById(phoneDetailsDto.getPhoneNumberOwner()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/addresses")
    public ResponseEntity<Void> addNewAddress(@RequestBody AddressDetailsDto addressDetailsDto) {
        addressService.addNewAddress(addressDetailsDto, contactService.findById(addressDetailsDto.getAddressOwner()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/phones/init-data")
    public ResponseEntity<PhoneRegistrationInitDataDto> getPhoneRegistrationInitData() {
        return new ResponseEntity<>(phoneNumberService.getPhoneRegistrationInitData(), HttpStatus.OK);
    }


    @Operation(summary = "Update one contact's basic data", description = "Endpoint for updating basic data" +
            " of a contact by contactId.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PutMapping("/contactDetails/{contactId}")
    public ResponseEntity<Void> updateContactById(@PathVariable Long contactId, @RequestBody ContactDetailsDto contactDetailsDto) {
        contactService.updateContactById(contactId, contactDetailsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Update one contact's full data", description = "Endpoint for updating full data" +
            " (including phones list and addresses list) of a contact by contactId.")
    @SecurityRequirement(name = "basicScheme", scopes = {"USER", "ADMIN"})
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PutMapping("/contact/{contactId}")
    public ResponseEntity<Void> updateFullContactById(@PathVariable Long contactId, @RequestBody ContactFullDataDto contactFullDataDto) {
        contactService.updateFullContactById(contactId, contactFullDataDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/phones/phones-list/{contactId}")
    public ResponseEntity<Void> replacePhonesList(@PathVariable Long contactId, @RequestBody List<PhoneDetailsDto> phoneDetailsDtos) {
        phoneNumberService.replacePhonesList(phoneDetailsDtos, contactService.findById(contactId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/addresses/addresses-list/{contactId}")
    public ResponseEntity<Void> replaceAddressesList(@PathVariable Long contactId, @RequestBody List<AddressDetailsDto> addressDetailsDtos) {
        addressService.replaceAddressesList(addressDetailsDtos, contactService.findById(contactId));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
