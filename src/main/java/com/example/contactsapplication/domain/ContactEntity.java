package com.example.contactsapplication.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contact")
@Data
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "mothers_name")
    private String mothersName;

    @Column(name = "ssid")
    private String ssId;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    private UserEntity contactOwner;

    @OneToMany(mappedBy = "addressOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "phoneNumberOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneNumberEntity> phoneNumbers;


}
