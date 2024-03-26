package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.Role;
import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.dto.out.AuthenticatedUserDto;
import com.example.contactsapplication.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserEntity userCreationDtoToUserEntity(UserCreationDto userCreationDto) {
        UserEntity userEntity = new UserEntity();
        log.info("ROLE: " + userCreationDto.getRole());
        log.info("ROLE value: " + Role.valueOf(userCreationDto.getRole()));
        log.info("EMAIL: " + userCreationDto.getEmail());
        log.info("FN: " + userCreationDto.getFirstName());
        log.info("LN: " + userCreationDto.getLastName());
        log.info("PW: " + userCreationDto.getPassword());
        userEntity.setRole(Role.valueOf(userCreationDto.getRole()));
        userEntity.setEmail(userCreationDto.getEmail());
        userEntity.setFirstName(userCreationDto.getFirstName());
        userEntity.setLastName(userCreationDto.getLastName());
        userEntity.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        return userEntity;
    }

    public AuthenticatedUserDto mapCustomUserDetailsToAuthenticatedUserDto(CustomUserDetails loggedInUserDetails) {
        AuthenticatedUserDto authenticatedUserDto = new AuthenticatedUserDto();
        authenticatedUserDto.setUserId(loggedInUserDetails.getUserId());
        authenticatedUserDto.setFirstName(loggedInUserDetails.getFirstName());
        authenticatedUserDto.setLastName(loggedInUserDetails.getLastName());
        authenticatedUserDto.setRole(loggedInUserDetails.getAuthorities().toArray()[0].toString());
        authenticatedUserDto.setEmail(loggedInUserDetails.getEmail());
        return authenticatedUserDto;
    }

}
