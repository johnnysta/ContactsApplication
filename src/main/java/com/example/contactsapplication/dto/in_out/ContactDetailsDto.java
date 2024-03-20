package com.example.contactsapplication.dto.in_out;

import com.example.contactsapplication.domain.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class ContactDetailsDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String mothersName;
    private String ssId;
    private String taxId;
    private String email;
    private Long userId;

}
