package com.example.contactsapplication.dto.in_out;

import lombok.Data;

import java.util.List;

@Data
public class PhoneRegistrationInitDataDto {

    List<String> usageTypeList;

    public PhoneRegistrationInitDataDto(List<String> usageTypeList) {
        this.usageTypeList = usageTypeList;
    }
}
