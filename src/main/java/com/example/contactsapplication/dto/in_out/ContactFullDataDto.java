package com.example.contactsapplication.dto.in_out;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ContactFullDataDto {
    ContactDetailsDto contactBasicData;
    ArrayList<PhoneDetailsDto> phoneList;
    ArrayList<AddressDetailsDto> addressList;
}
