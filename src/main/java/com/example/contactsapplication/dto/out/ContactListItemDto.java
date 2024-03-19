package com.example.contactsapplication.dto.out;

import lombok.Data;

import java.util.Date;

@Data
public class ContactListItemDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String mothersName;

    private String ssId;

    private String taxId;

    private String email;

    private Long contactOwnerId;

}
