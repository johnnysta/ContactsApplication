package com.example.contactsapplication.dto.in_out;

import lombok.Data;

@Data
public class PhoneDetailsDto {
    Long id;
    String phoneUseType;
    String phoneNumber;
    String note;
    Long phoneNumberOwner;
    Boolean isDeleted;
}
