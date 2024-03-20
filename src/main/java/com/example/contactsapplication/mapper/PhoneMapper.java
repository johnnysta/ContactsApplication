package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.PhoneNumberEntity;
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
        return phoneDetailsDto;
    }
}
