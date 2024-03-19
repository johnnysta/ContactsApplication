package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.domain.UserEntity;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.mapper.ContactMapper;
import com.example.contactsapplication.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ContactService {

    ContactRepository contactRepository;
    UserService userService;
    ContactMapper contactMapper;

    public List<ContactListItemDto> getContactsByUserId(Long userId) {
        UserEntity userEntity = userService.getUserById(userId);
        List<ContactEntity> resultContacts = contactRepository.findAllByContactOwner(userEntity);
        List<ContactListItemDto> resultContactListItemDtos = new ArrayList<>();
        resultContacts.forEach(contactEntity ->
                resultContactListItemDtos.add(contactMapper.mapContactEntityToContactListItemDto(contactEntity)));
        return resultContactListItemDtos;
    }
}
