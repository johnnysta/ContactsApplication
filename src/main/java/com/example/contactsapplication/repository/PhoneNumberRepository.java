package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {

    List<PhoneNumberEntity> findByPhoneNumberOwnerId(Long phoneNumberOwnerId);
}
