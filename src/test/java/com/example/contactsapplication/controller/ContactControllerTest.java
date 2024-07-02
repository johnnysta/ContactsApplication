package com.example.contactsapplication.controller;

import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.service.AddressService;
import com.example.contactsapplication.service.ContactService;
import com.example.contactsapplication.service.PhoneNumberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ContactController.class)
class ContactControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @MockBean
    private AddressService addressService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetContactById() throws Exception {
        // Given
        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setId(1L);
        contactDetailsDto.setFirstName("John");
        contactDetailsDto.setLastName("Doe");

        Mockito.when(contactService.getContactById(anyLong())).thenReturn(contactDetailsDto);

        // When and Then
        mockMvc.perform(get("/api/contacts/contactDetails/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
}