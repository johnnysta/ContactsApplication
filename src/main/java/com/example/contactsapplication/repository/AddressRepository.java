package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository <AddressEntity, Long> {
}
