package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.Role;
import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserCreationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {

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
        userEntity.setPassword(userCreationDto.getPassword());
        return userEntity;
    }
}
