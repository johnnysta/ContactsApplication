package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactMapper {


    public ContactListItemDto mapContactEntityToContactListItemDto(ContactEntity contactEntity){
        ContactListItemDto  contactListItemDto = new ContactListItemDto();
        contactListItemDto.setId(contactEntity.getId());
        contactListItemDto.setFirstName(contactEntity.getFirstName());
        contactListItemDto.setLastName(contactEntity.getLastName());
        contactListItemDto.setEmail(contactEntity.getEmail());
        contactListItemDto.setSsId(contactEntity.getSsId());
        contactListItemDto.setTaxId(contactEntity.getTaxId());
        contactListItemDto.setBirthDate(contactEntity.getBirthDate());
        contactListItemDto.setMothersName(contactEntity.getMothersName());
        contactListItemDto.setContactOwnerId(contactEntity.getContactOwner().getId());
        return contactListItemDto;

    }
}
