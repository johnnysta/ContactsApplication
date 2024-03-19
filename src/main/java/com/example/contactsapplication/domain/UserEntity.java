package com.example.contactsapplication.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Table(name = "app_user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "You must provide a name")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "You must provide the first name")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "You must provide the last name")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "You must provide an email address")
    @Column(name = "email")
    private String email;

}
