package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactMapper {


    public ContactListItemDto mapContactEntityToContactListItemDto(ContactEntity contactEntity){
        ContactListItemDto  contactListItemDto = new ContactListItemDto();
        contactListItemDto.setId(contactListItemDto.getId());
        contactListItemDto.setFirstName(contactEntity.getFirstName());
        contactListItemDto.setLastName(contactListItemDto.getLastName());
        contactListItemDto.setEmail(contactListItemDto.getEmail());
        contactListItemDto.setSsId(contactListItemDto.getSsId());
        contactListItemDto.setTaxId(contactListItemDto.getTaxId());
        contactListItemDto.setBirthDate(contactListItemDto.getBirthDate());
        contactListItemDto.setMothersName(contactListItemDto.getMothersName());
        contactListItemDto.setContactOwnerId(contactEntity.getContactOwner().getId());
        return contactListItemDto;

    }
}
