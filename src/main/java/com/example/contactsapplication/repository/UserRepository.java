package com.example.contactsapplication.repository;

import com.example.contactsapplication.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByEmail(String email);
}
