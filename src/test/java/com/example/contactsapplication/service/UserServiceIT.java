package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserChangePasswordDto;
import com.example.contactsapplication.dto.in.UserCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void testGetUserById() {
    }

    @Test
    void testAddUser() {
        //        given
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setEmail("user@user.hu");
        userCreationDto.setRole("USER");
        userCreationDto.setPassword("test");
        userCreationDto.setLastName("Hopkins");
        userCreationDto.setFirstName("Tuskó");
        //        when
        UserEntity userEntity = userService.addUser(userCreationDto);
        //        then
        assertEquals(userEntity.getEmail(), userCreationDto.getEmail());
        assertEquals(userEntity.getRole().name(), userCreationDto.getRole());
        Assertions.assertTrue(bCryptPasswordEncoder.matches(userCreationDto.getPassword(), userEntity.getPassword()));
        assertEquals(userEntity.getLastName(), userCreationDto.getLastName());
        assertEquals(userEntity.getFirstName(), userCreationDto.getFirstName());
    }

    @Test
    void testFindById() {
    }

    @Test
    void testChangePassword() {
        //        given
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setEmail("user@user.hu");
        userCreationDto.setRole("USER");
        userCreationDto.setPassword("original_pw");
        userCreationDto.setLastName("Hopkins");
        userCreationDto.setFirstName("Tuskó");
        UserEntity userEntity = userService.addUser(userCreationDto);
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        UserChangePasswordDto userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setOldPassword("original_pw");
        userChangePasswordDto.setNewPassword("changed_pw");
        //        when
        UserEntity userEntityResult = userService.changePassword(userChangePasswordDto, customUserDetails);
        //        then
        Assertions.assertTrue(bCryptPasswordEncoder.matches(userChangePasswordDto.getNewPassword(), userEntityResult.getPassword()));
    }
}
