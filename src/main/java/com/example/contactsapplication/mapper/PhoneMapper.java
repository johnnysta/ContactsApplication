package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.PhoneNumberEntity;
import com.example.contactsapplication.domain.PhoneUseType;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class PhoneMapper {
    public PhoneDetailsDto mapPhoneEntityToPhoneDetailsDto(PhoneNumberEntity phoneEntity) {
        PhoneDetailsDto phoneDetailsDto = new PhoneDetailsDto();
        phoneDetailsDto.setPhoneNumber(phoneEntity.getPhoneNumber());
        phoneDetailsDto.setPhoneUseType(phoneEntity.getPhoneUseType().name());
        phoneDetailsDto.setNote(phoneEntity.getNote());
        phoneDetailsDto.setId(phoneEntity.getId());
        phoneDetailsDto.setPhoneNumberOwner(phoneEntity.getPhoneNumberOwner().getId());
        phoneDetailsDto.setIsDeleted(false);
        return phoneDetailsDto;
    }

    public PhoneNumberEntity mapPhoneDetailsDtoToPhoneNumberEntity(PhoneDetailsDto phoneDetailsDto) {
        PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
        phoneNumberEntity.setPhoneNumber(phoneDetailsDto.getPhoneNumber());
        phoneNumberEntity.setNote(phoneDetailsDto.getNote());
        phoneNumberEntity.setPhoneUseType(PhoneUseType.valueOf(phoneDetailsDto.getPhoneUseType()));
        return phoneNumberEntity;
    }
}
