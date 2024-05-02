package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.domain.PhoneNumberEntity;
import com.example.contactsapplication.domain.PhoneUseType;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneRegistrationInitDataDto;
import com.example.contactsapplication.mapper.PhoneMapper;
import com.example.contactsapplication.repository.PhoneNumberRepository;
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
public class  PhoneNumberService {

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
        log.info(phoneNumberEntity.getPhoneUseType().toString());
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

    public void replacePhonesList(Long contactId, List<PhoneDetailsDto> phoneDetailsDtos) {
        phoneDetailsDtos.forEach(phoneDetailsDto -> {
            Long currentId = phoneDetailsDto.getId();
            Boolean isDeleted = phoneDetailsDto.getIsDeleted();
            if (isDeleted) {
                phoneNumberRepository.deleteById(currentId);
            } else if (phoneDetailsDto.getId() == 0) {
                ContactEntity phoneNumberOwner = contactService.findById(contactId);
                PhoneNumberEntity phoneNumberEntity = phoneMapper.mapPhoneDetailsDtoToPhoneNumberEntity(phoneDetailsDto);
                phoneNumberEntity.setPhoneNumberOwner(phoneNumberOwner);
                phoneNumberRepository.save(phoneNumberEntity);
            } else {
                PhoneNumberEntity phoneNumberEntity = phoneNumberRepository.findById(currentId).orElseThrow(EntityNotFoundException::new);
                phoneNumberEntity.setPhoneNumber(phoneDetailsDto.getPhoneNumber());
                phoneNumberEntity.setNote(phoneDetailsDto.getNote());
                phoneNumberEntity.setPhoneUseType(PhoneUseType.valueOf(phoneDetailsDto.getPhoneUseType()));
                phoneNumberRepository.save(phoneNumberEntity);
            }
        });
    }
}
