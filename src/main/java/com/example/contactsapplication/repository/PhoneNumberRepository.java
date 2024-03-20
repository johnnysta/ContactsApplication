package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {
}
