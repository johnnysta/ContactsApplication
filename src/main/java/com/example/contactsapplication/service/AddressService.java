package com.example.contactsapplication.service;

import com.example.contactsapplication.domain.AddressEntity;
import com.example.contactsapplication.domain.ContactEntity;
import com.example.contactsapplication.dto.in_out.AddressDetailsDto;
import com.example.contactsapplication.mapper.AddressMapper;
import com.example.contactsapplication.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public void deleteAddressById(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    public void addNewAddress(AddressDetailsDto addressDetailsDto, ContactEntity addressOwner) {
        AddressEntity addressEntity = addressMapper.mapAddressDetailsDtoToAddressEntity(addressDetailsDto);
        addressEntity.setAddressOwner(addressOwner);
        addressRepository.save(addressEntity);
    }

    public void replaceAddressesList(List<AddressDetailsDto> addressDetailsDtos, ContactEntity addressOwner) {
        addressDetailsDtos.forEach(addressDetailsDto -> {
            Long currentId = addressDetailsDto.getId();
            Boolean isDeleted = addressDetailsDto.getIsDeleted();
            if (isDeleted) {
                addressRepository.deleteById(currentId);
            } else if (addressDetailsDto.getId() == 0) {
                AddressEntity addressEntity = addressMapper.mapAddressDetailsDtoToAddressEntity(addressDetailsDto);
                addressEntity.setAddressOwner(addressOwner);
                addressRepository.save(addressEntity);
            } else {
                AddressEntity addressEntity = addressRepository.findById(currentId).orElseThrow(EntityNotFoundException::new);
                addressEntity = addressMapper.mapAddressDetailsDtoToAddressEntity(addressDetailsDto, addressEntity);
                addressRepository.save(addressEntity);
            }
        });
    }
}
