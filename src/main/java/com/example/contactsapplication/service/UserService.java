package com.example.contactsapplication.service;


import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.mapper.UserMapper;
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
    UserMapper userMapper;

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void addUser(UserCreationDto userCreationDto) {
        UserEntity userEntity = userMapper.userCreationDtoToUserEntity(userCreationDto);
        userRepository.save(userEntity);
    }


    public UserEntity findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }


}
