package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    List<ContactEntity> findAllByContactOwner(Long userId);
}
