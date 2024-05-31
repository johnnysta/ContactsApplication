package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in.UserCreationDto;
import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneRegistrationInitDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@SpringBootTest
@Transactional
@Rollback
class PhoneNumberServiceIT {


    @Autowired
    PhoneNumberService phoneNumberService;
    @Autowired
    UserService userService;
    @Autowired
    ContactService contactService;

    @Test
    void testGetPhonesByContactId() {
    }

    @Test
    void testDeletePhoneById() {
    }

    @Test
    void testAddNewPhone() {
    }

    @Test
    void testGetPhoneRegistrationInitData() {
        //given
        String[] expectedUsageTypes = {"PRIMARY", "WORK", "HOME", "OTHER" };
        List<String> expectedUsageTypeList = Arrays.asList(expectedUsageTypes);
        //when
        PhoneRegistrationInitDataDto phoneRegistrationInitDataDto = phoneNumberService.getPhoneRegistrationInitData();
        //then
        assertIterableEquals(expectedUsageTypeList, phoneRegistrationInitDataDto.getUsageTypeList());
    }

    @Test
    void testReplacePhonesList_AddOnePhone() {
        //given
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setFirstName("Tibor");
        userCreationDto.setLastName("Kiss");
        userCreationDto.setRole("USER");
        userCreationDto.setPassword("majom");
        userCreationDto.setEmail("tibor.kiss@bblabla.hu");
        UserEntity currentUser = userService.addUser(userCreationDto);

        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setUserId(currentUser.getId());
        contactDetailsDto.setFirstName("Dezső");
        contactDetailsDto.setEmail("dezső@skfhsk.hu");

        Long currentContactId = contactService.addNewContact(contactDetailsDto).getId();

        PhoneDetailsDto phoneDetailsDto1 = new PhoneDetailsDto();
        phoneDetailsDto1.setPhoneNumber("12345");
        phoneDetailsDto1.setPhoneUseType("WORK");
        phoneDetailsDto1.setIsDeleted(false);
        phoneDetailsDto1.setPhoneNumberOwner(currentContactId);
        phoneDetailsDto1.setId(0L);

        List<PhoneDetailsDto> phonesList = new ArrayList<>();
        phonesList.add(phoneDetailsDto1);

        //when
        phoneNumberService.replacePhonesList(phonesList, contactService.findById(currentContactId));
        List<PhoneDetailsDto> resultList = phoneNumberService.getPhonesByContactId(currentContactId);

        //then
        assertEquals(1, resultList.size());
        assertEquals("12345", resultList.get(0).getPhoneNumber());
    }


    @Test
    void testReplacePhonesList_AddOnePhoneThenModifyIt() {
        //given
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setFirstName("Tibor");
        userCreationDto.setLastName("Kiss");
        userCreationDto.setRole("USER");
        userCreationDto.setPassword("majom");
        userCreationDto.setEmail("tibor.kiss@bblabla.hu");
        UserEntity currentUser = userService.addUser(userCreationDto);

        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setUserId(currentUser.getId());
        contactDetailsDto.setFirstName("Dezső");
        contactDetailsDto.setEmail("dezső@skfhsk.hu");

        Long currentContactId = contactService.addNewContact(contactDetailsDto).getId();

        PhoneDetailsDto phoneDetailsDto1 = new PhoneDetailsDto();
        phoneDetailsDto1.setPhoneNumber("12345");
        phoneDetailsDto1.setPhoneUseType("WORK");
        phoneDetailsDto1.setIsDeleted(false);
        phoneDetailsDto1.setPhoneNumberOwner(currentContactId);
        phoneDetailsDto1.setId(0L);

        List<PhoneDetailsDto> phonesList = new ArrayList<>();
        phonesList.add(phoneDetailsDto1);

        phoneNumberService.replacePhonesList(phonesList, contactService.findById(currentContactId));
        List<PhoneDetailsDto> resultList = phoneNumberService.getPhonesByContactId(currentContactId);

        PhoneDetailsDto phoneDetailsDto2 = new PhoneDetailsDto();
        phoneDetailsDto2.setPhoneNumber("54321");
        phoneDetailsDto2.setPhoneUseType(resultList.get(0).getPhoneUseType());
        phoneDetailsDto2.setIsDeleted(resultList.get(0).getIsDeleted());
        phoneDetailsDto2.setPhoneNumberOwner(resultList.get(0).getPhoneNumberOwner());
        phoneDetailsDto2.setId(resultList.get(0).getId());
        phoneDetailsDto2.setNote(resultList.get(0).getNote());

        List<PhoneDetailsDto> phonesList2 = new ArrayList<>();
        phonesList2.add(phoneDetailsDto2);

        //when
        phoneNumberService.replacePhonesList(phonesList2, contactService.findById(currentContactId));
        resultList = phoneNumberService.getPhonesByContactId(currentContactId);

        //then
        assertEquals(1, resultList.size());
        assertEquals("54321", resultList.get(0).getPhoneNumber());
    }


    @Test
    void testReplacePhonesList_AddTwoPhones() {
        //given
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setFirstName("Tibor");
        userCreationDto.setLastName("Kiss");
        userCreationDto.setRole("USER");
        userCreationDto.setPassword("majom");
        userCreationDto.setEmail("tibor.kiss@bblabla.hu");
        UserEntity currentUser = userService.addUser(userCreationDto);

        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setUserId(currentUser.getId());
        contactDetailsDto.setFirstName("Dezső");
        contactDetailsDto.setEmail("dezső@skfhsk.hu");

        Long currentContactId = contactService.addNewContact(contactDetailsDto).getId();

        PhoneDetailsDto phoneDetailsDto1 = new PhoneDetailsDto();
        phoneDetailsDto1.setPhoneNumber("12345");
        phoneDetailsDto1.setPhoneUseType("WORK");
        phoneDetailsDto1.setIsDeleted(false);
        phoneDetailsDto1.setPhoneNumberOwner(currentContactId);
        phoneDetailsDto1.setId(0L);

        PhoneDetailsDto phoneDetailsDto2 = new PhoneDetailsDto();
        phoneDetailsDto2.setPhoneNumber("54321");
        phoneDetailsDto2.setPhoneUseType("WORK");
        phoneDetailsDto2.setIsDeleted(false);
        phoneDetailsDto2.setPhoneNumberOwner(currentContactId);
        phoneDetailsDto2.setId(0L);

        List<PhoneDetailsDto> phonesList = new ArrayList<>();
        phonesList.add(phoneDetailsDto1);
        phonesList.add(phoneDetailsDto2);

        phoneNumberService.replacePhonesList(phonesList, contactService.findById(currentContactId));

        List<PhoneDetailsDto> resultList = phoneNumberService.getPhonesByContactId(currentContactId);

        assertEquals(2, resultList.size());
        assertEquals("12345", resultList.get(0).getPhoneNumber());
        assertEquals("54321", resultList.get(1).getPhoneNumber());
    }

    @Test
    void testReplacePhonesList_AddTwoPhonesThenDeleteOne() {
        //given
        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setFirstName("Tibor");
        userCreationDto.setLastName("Kiss");
        userCreationDto.setRole("USER");
        userCreationDto.setPassword("majom");
        userCreationDto.setEmail("tibor.kiss@bblabla.hu");
        UserEntity currentUser = userService.addUser(userCreationDto);

        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setUserId(currentUser.getId());
        contactDetailsDto.setFirstName("Dezső");
        contactDetailsDto.setEmail("dezső@skfhsk.hu");

        Long currentContactId = contactService.addNewContact(contactDetailsDto).getId();

        PhoneDetailsDto phoneDetailsDto1 = new PhoneDetailsDto();
        phoneDetailsDto1.setPhoneNumber("12345");
        phoneDetailsDto1.setPhoneUseType("WORK");
        phoneDetailsDto1.setIsDeleted(false);
        phoneDetailsDto1.setPhoneNumberOwner(currentContactId);
        phoneDetailsDto1.setId(0L);

        PhoneDetailsDto phoneDetailsDto2 = new PhoneDetailsDto();
        phoneDetailsDto2.setPhoneNumber("54321");
        phoneDetailsDto2.setPhoneUseType("WORK");
        phoneDetailsDto2.setIsDeleted(false);
        phoneDetailsDto2.setPhoneNumberOwner(currentContactId);
        phoneDetailsDto2.setId(0L);

        List<PhoneDetailsDto> phonesList = new ArrayList<>();
        phonesList.add(phoneDetailsDto1);
        phonesList.add(phoneDetailsDto2);

        phoneNumberService.replacePhonesList(phonesList, contactService.findById(currentContactId));
        phonesList = phoneNumberService.getPhonesByContactId(currentContactId);

        //when
        phonesList.get(1).setIsDeleted(true);
        phoneNumberService.replacePhonesList(phonesList, contactService.findById(currentContactId));
        List<PhoneDetailsDto> resultList = phoneNumberService.getPhonesByContactId(currentContactId);

        //then
        assertEquals(1, resultList.size());
        assertEquals("12345", resultList.get(0).getPhoneNumber());
    }


}
