package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
}
