package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.domain.PhoneNumberEntity;
import com.example.contactsapplication.domain.PhoneUseType;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneRegistrationInitDataDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.mapper.PhoneMapper;
import com.example.contactsapplication.repository.PhoneNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PhoneNumberService {

    ContactService contactService;
    PhoneNumberRepository phoneNumberRepository;
    PhoneMapper phoneMapper;

    public List<PhoneDetailsDto> getPhonesByContactId(Long contactId) {
        List<PhoneNumberEntity> phoneNumbers = phoneNumberRepository.findByPhoneNumberOwnerId(contactId);
        List<PhoneDetailsDto> resultPhoneDetailsDtos = new ArrayList<>();
        phoneNumbers.forEach(phoneEntity ->
                resultPhoneDetailsDtos.add(phoneMapper.mapPhoneEntityToPhoneDetailsDto(phoneEntity)));
        return resultPhoneDetailsDtos;
    }

    public void deletePhoneById(Long phoneId) {
        phoneNumberRepository.deleteById(phoneId);
    }

    public void addNewPhone(PhoneDetailsDto phoneDetailsDto) {
        ContactEntity phoneNumberOwner = contactService.findById(phoneDetailsDto.getPhoneNumberOwner());
        PhoneNumberEntity phoneNumberEntity = phoneMapper.mapPhoneDetailsDtoToPhoneNumberEntity(phoneDetailsDto);
        phoneNumberEntity.setPhoneNumberOwner(phoneNumberOwner);
        phoneNumberRepository.save(phoneNumberEntity);
    }

    public PhoneRegistrationInitDataDto getPhoneRegistrationInitData() {
        List<String> initDataList = new ArrayList<>();
        PhoneRegistrationInitDataDto phoneRegistrationInitDataDto = new PhoneRegistrationInitDataDto(initDataList);
        for (PhoneUseType phoneUseType : PhoneUseType.values()) {
            initDataList.add(phoneUseType.name());
        }
        return phoneRegistrationInitDataDto;
    }
}
