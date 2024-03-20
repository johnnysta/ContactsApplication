package com.example.contactsapplication.mapper;

import com.example.contactsapplication.domain.AddressEntity;
import com.example.contactsapplication.dto.in_out.AddressDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {


    public AddressDetailsDto mapAddressEntityToAddressDetaildDto(AddressEntity addressEntity) {
        AddressDetailsDto addressDetailsDto = new AddressDetailsDto();

        addressDetailsDto.setId(addressEntity.getId());
        addressDetailsDto.setNote(addressEntity.getNote());
        addressDetailsDto.setCity(addressEntity.getCity());
        addressDetailsDto.setZipCode(addressEntity.getZipCode());
        addressDetailsDto.setStreet(addressEntity.getStreet());
        addressDetailsDto.setHouseNumber(addressEntity.getHouseNumber());
        addressDetailsDto.setAddressOwner(addressEntity.getAddressOwner().getId());

        return addressDetailsDto;

    }
}
