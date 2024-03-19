package com.example.contactsapplication.service;


import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
