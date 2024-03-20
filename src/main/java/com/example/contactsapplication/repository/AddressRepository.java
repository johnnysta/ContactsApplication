package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository <AddressEntity, Long> {
    List<AddressEntity> findByAddressOwnerId(Long contactId);
}
