package com.example.contactsapplication.service;


import com.example.contactsapplication.domain.Role;
import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserChangePasswordDto;
import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.exception.custom.InvalidPasswordException;
import com.example.contactsapplication.mapper.UserMapper;
import com.example.contactsapplication.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public UserEntity addUser(UserCreationDto userCreationDto) {
        UserEntity userEntity = userMapper.userCreationDtoToUserEntity(userCreationDto);
        return userRepository.save(userEntity);
    }

    public UserEntity findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }


    public UserEntity changePassword(UserChangePasswordDto userChangePasswordDto, CustomUserDetails customUserDetails) {
        log.info("Old PW: " + userChangePasswordDto.getOldPassword());
        log.info("Old PW from Db: " + customUserDetails.getPassword());
        if (customUserDetails.getPassword() != null && passwordEncoder.matches(userChangePasswordDto.getOldPassword(), customUserDetails.getPassword())) {
            UserEntity userEntity = findById(customUserDetails.getUserId());
            userEntity.setPassword(passwordEncoder.encode(userChangePasswordDto.getNewPassword()));
            Role currentRole = userEntity.getRole();
            if (currentRole.name().substring(0, 4).equals("NEW_")) {
                userEntity.setRole(Role.valueOf(currentRole.name().substring(4)));
            }
            UserEntity userEntityResult = userRepository.save((userEntity));
            return userEntityResult;
        } else {
            throw new InvalidPasswordException("Invalid password!");
        }
    }
}
