package com.example.contactsapplication.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "note")
    private String note;

    @ManyToOne
    private ContactEntity addressOwner;

}
