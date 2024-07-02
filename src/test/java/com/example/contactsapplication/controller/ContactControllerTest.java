package com.example.contactsapplication.controller;

import com.example.contactsapplication.dto.in_out.ContactDetailsDto;
import com.example.contactsapplication.dto.out.ContactListItemDto;
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

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetContactsByUserId() throws Exception {
        // Given
        ContactListItemDto contactListItemDto = new ContactListItemDto();
        contactListItemDto.setId(1L);
        contactListItemDto.setFirstName("John");
        contactListItemDto.setLastName("Doe");
        List<ContactListItemDto> results = new ArrayList<>();
        results.add(contactListItemDto);

        Mockito.when(contactService.getContactsByUserId(anyLong())).thenReturn(results);

        // When and Then
        mockMvc.perform(get("/api/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }
}