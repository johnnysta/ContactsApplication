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
        addressDetailsDto.setIsDeleted(false);
        return addressDetailsDto;
    }

    public AddressEntity mapAddressDetailsDtoToAddressEntity(AddressDetailsDto addressDetailsDto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setZipCode(addressDetailsDto.getZipCode());
        addressEntity.setCity(addressDetailsDto.getCity());
        addressEntity.setStreet(addressDetailsDto.getStreet());
        addressEntity.setHouseNumber(addressDetailsDto.getHouseNumber());
        addressEntity.setNote(addressDetailsDto.getNote());
        return addressEntity;
    }

    public AddressEntity mapAddressDetailsDtoToAddressEntity(AddressDetailsDto addressDetailsDto, AddressEntity addressEntity) {
        addressEntity.setZipCode(addressDetailsDto.getZipCode());
        addressEntity.setCity(addressDetailsDto.getCity());
        addressEntity.setStreet(addressDetailsDto.getStreet());
        addressEntity.setHouseNumber(addressDetailsDto.getHouseNumber());
        addressEntity.setNote(addressDetailsDto.getNote());
        return addressEntity;
    }
}
