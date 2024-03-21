package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ContactListItemDto mapContactEntityToContactListItemDto(ContactEntity contactEntity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ContactListItemDto contactListItemDto = new ContactListItemDto();
        contactListItemDto.setId(contactEntity.getId());
        contactListItemDto.setFirstName(contactEntity.getFirstName());
        contactListItemDto.setLastName(contactEntity.getLastName());
        contactListItemDto.setEmail(contactEntity.getEmail());
        contactListItemDto.setSsId(contactEntity.getSsId());
        contactListItemDto.setTaxId(contactEntity.getTaxId());
        LocalDate birthDate = contactEntity.getBirthDate();
        if (birthDate != null) {
            contactListItemDto.setBirthDate(birthDate.format(formatter));
        } else {
            contactListItemDto.setBirthDate("");
        }
        contactListItemDto.setMothersName(contactEntity.getMothersName());
        contactListItemDto.setContactOwnerId(contactEntity.getContactOwner().getId());
        return contactListItemDto;

    }

    public ContactEntity mapContactDetailDtoToContactEntity(ContactDetailsDto contactDetailsDto) {
        ContactEntity contactEntity = new ContactEntity();

        contactEntity.setFirstName(contactDetailsDto.getFirstName());
        contactEntity.setLastName(contactDetailsDto.getLastName());
        contactEntity.setEmail(contactDetailsDto.getEmail());
        contactEntity.setBirthDate(LocalDate.parse(contactDetailsDto.getBirthDate(), formatter));
        contactEntity.setMothersName(contactDetailsDto.getMothersName());
        contactEntity.setSsId(contactDetailsDto.getSsId());
        contactEntity.setTaxId(contactDetailsDto.getTaxId());
        return contactEntity;
    }

    public ContactDetailsDto mapContactEntityToContactDetailsDto(ContactEntity contactEntity) {
        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();

        contactDetailsDto.setFirstName(contactEntity.getFirstName());
        contactDetailsDto.setLastName(contactEntity.getLastName());
        contactDetailsDto.setEmail(contactEntity.getEmail());
        LocalDate birthDate = contactEntity.getBirthDate();
        if (birthDate != null) {
            contactDetailsDto.setBirthDate(birthDate.format(formatter));
        } else {
            contactDetailsDto.setBirthDate("");
        }
        contactDetailsDto.setMothersName(contactEntity.getMothersName());
        contactDetailsDto.setSsId(contactEntity.getSsId());
        contactDetailsDto.setTaxId(contactEntity.getTaxId());
        contactDetailsDto.setId(contactEntity.getId());
        contactDetailsDto.setUserId(contactEntity.getContactOwner().getId());
        return contactDetailsDto;
    }

    public void mapFromContactDetailsDtoToExistingContactEntity(ContactDetailsDto contactDetailsDto,
                                                                ContactEntity contactFound) {
        contactFound.setFirstName(contactDetailsDto.getFirstName());
        contactFound.setLastName(contactDetailsDto.getLastName());
        contactFound.setEmail(contactDetailsDto.getEmail());

        contactFound.setBirthDate(LocalDate.parse(contactDetailsDto.getBirthDate(), formatter));

        contactFound.setMothersName(contactDetailsDto.getMothersName());
        contactFound.setSsId(contactDetailsDto.getSsId());
        contactFound.setTaxId(contactDetailsDto.getTaxId());
    }
}
