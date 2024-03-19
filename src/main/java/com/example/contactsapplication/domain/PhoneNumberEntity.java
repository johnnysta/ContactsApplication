package com.example.contactsapplication.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phone_number")
@Data
public class PhoneNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_use_type")
    @Enumerated(EnumType.STRING)
    private PhoneUseType phoneUseType;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private ContactEntity phoneNumberOwner;

}
