package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.AddressEntity;
import com.example.contactsapplication.domain.PhoneNumberEntity;
import com.example.contactsapplication.dto.in_out.AddressDetailsDto;
import com.example.contactsapplication.dto.in_out.PhoneDetailsDto;
import com.example.contactsapplication.mapper.AddressMapper;
import com.example.contactsapplication.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AddressService {

    AddressRepository addressRepository;
    AddressMapper addressMapper;

    public List<AddressDetailsDto> getAddressesByContactId(Long contactId) {
        List<AddressEntity> addresses = addressRepository.findByAddressOwnerId(contactId);
        List<AddressDetailsDto> resultAddressDtos = new ArrayList<>();
        addresses.forEach(addressEntity -> resultAddressDtos.add(addressMapper.mapAddressEntityToAddressDetaildDto(addressEntity)));
        return resultAddressDtos;
    }


}
