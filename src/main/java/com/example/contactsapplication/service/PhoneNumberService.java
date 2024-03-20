package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.PhoneNumberEntity;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
import com.example.contactsapplication.mapper.PhoneMapper;
import com.example.contactsapplication.repository.PhoneNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PhoneNumberService {

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
}
