package com.example.contactsapplication.dto.in_out;

import lombok.Data;

@Data
public class AddressDetailsDto {
    Long id;
    String zipCode;
    String city;
    String street;
    String houseNumber;
    String note;
    Long addressOwner;
    Boolean isDeleted;
}
