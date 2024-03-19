package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.Role;
import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserCreationDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity userCreationDtoToUserEntity(UserCreationDto userCreationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(Role.valueOf(userCreationDto.getRole()));
        userEntity.setEmail(userCreationDto.getEmail());
        userEntity.setFirstName(userCreationDto.getFirstName());
        userEntity.setLastName(userCreationDto.getLastName());
        userEntity.setPassword(userCreationDto.getPassword());
        return userEntity;
    }
}
