package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.dto.in_out.ContactFullDataDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.mapper.ContactMapper;
import com.example.contactsapplication.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ContactService {

    ContactRepository contactRepository;
    UserService userService;
    AddressService addressService;
    PhoneNumberService phoneNumberService;
    ContactMapper contactMapper;


    public List<ContactListItemDto> getContactsByUserId(Long userId) {
        UserEntity userEntity = userService.getUserById(userId);
        List<ContactEntity> resultContacts = contactRepository.findAllByContactOwner(userEntity);
        List<ContactListItemDto> resultContactListItemDtos = new ArrayList<>();
        resultContacts.forEach(contactEntity ->
                resultContactListItemDtos.add(contactMapper.mapContactEntityToContactListItemDto(contactEntity)));
        return resultContactListItemDtos;
    }

    public void deleteContactById(Long contactId) {
        contactRepository.deleteById(contactId);
    }

    public ContactEntity addNewContact(ContactDetailsDto contactDetailsDto) {
        UserEntity user = userService.findById(contactDetailsDto.getUserId());
        ContactEntity contactEntity = contactMapper.mapContactDetailDtoToContactEntity(contactDetailsDto);
        contactEntity.setContactOwner(user);
        return contactRepository.save(contactEntity);
    }

    public ContactEntity findById(Long id) {
        return contactRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public ContactDetailsDto getContactById(Long contactId) {
        ContactEntity contactEntity = contactRepository.findById(contactId).orElseThrow(EntityNotFoundException::new);
        return contactMapper.mapContactEntityToContactDetailsDto(contactEntity);
    }

    public void updateContactById(Long contactId, ContactDetailsDto contactDetailsDto) {
        ContactEntity contactFound = contactRepository.findById(contactId).orElseThrow(EntityNotFoundException::new);
        contactMapper.mapFromContactDetailsDtoToExistingContactEntity(contactDetailsDto, contactFound);
        contactRepository.save(contactFound);
    }

    public ContactFullDataDto getFullContactById(Long contactId) {
        ContactEntity contactEntity = contactRepository.findById(contactId).orElseThrow(EntityNotFoundException::new);
        log.info("Contact email from database: " + contactEntity.getEmail());
        return contactMapper.mapContactEntityToContactFullDataDto(contactEntity);
    }

    public void updateFullContactById(Long contactId, ContactFullDataDto contactFullDataDto) {
        updateContactById(contactId, contactFullDataDto.getContactBasicData());
        addressService.replaceAddressesList(contactFullDataDto.getAddressList(), findById(contactId));
        phoneNumberService.replacePhonesList(contactFullDataDto.getPhoneList(), findById(contactId));
    }

    public Long addNewFullContact(ContactFullDataDto contactFullDataDto) {
        ContactEntity newContactEntity = addNewContact(contactFullDataDto.getContactBasicData());
        addressService.replaceAddressesList(contactFullDataDto.getAddressList(), newContactEntity);
        phoneNumberService.replacePhonesList(contactFullDataDto.getPhoneList(), newContactEntity);
        return newContactEntity.getId();
    }
}
