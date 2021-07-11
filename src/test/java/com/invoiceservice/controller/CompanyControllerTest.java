package com.invoiceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoiceservice.security.login.domain.ERole;
import com.invoiceservice.security.login.domain.Role;
import com.invoiceservice.security.login.domain.User;
import com.invoiceservice.security.login.payload.response.JwtResponse;
import com.invoiceservice.security.login.repository.RoleRepository;
import com.invoiceservice.security.login.repository.UserRepository;
import com.invoiceservice.domain.Company;
import com.invoiceservice.domain.CompanyDto;
import com.invoiceservice.service.DbService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CompanyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DbService dbService;

    private String token;
    private String type;
    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void shouldAddUserAndGetToken() throws Exception {
        //given
        user = new User("admin_test2333832", passwordEncoder.encode("admin_test2333832"));
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);
        user.setRoles(roles);
        
        //when
        userRepository.save(user);
        MvcResult login = mockMvc.perform(post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"username\": \"admin_test2333832\", \"password\": \"admin_test2333832\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        String json = login.getResponse().getContentAsString();
        JwtResponse jwtResponse = new ObjectMapper().readValue(json, JwtResponse.class);
        token = jwtResponse.getToken();
        type = jwtResponse.getType();
        type = type + " ";

        //then
        Assert.assertTrue(token.length() > 10);
        Assert.assertTrue(type.length() > 3);
    }

    @AfterEach
    public void shouldRemoveUser(){
        userRepository.delete(user);
    }

    @Test
    public void shouldAddCompany() throws Exception {
        //given
        CompanyDto companyDto = new CompanyDto(123456788888L,"FIRMA_TEST", "14785", "KRAKOW", "POZIOMKOWA 19"  );

        //when
        MvcResult response = mockMvc.perform(post("/company")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", type + token)
                .content( objectMapper.writeValueAsString(companyDto)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();
        Optional<Company> company = dbService.getCompanyByTaxId(companyDto.getTaxId());

        //then
        Assert.assertEquals(company.get().getName(), companyDto.getName());
        Assert.assertEquals(company.get().getPostalCode(), companyDto.getPostalCode());
        Assert.assertEquals(company.get().getCity(),companyDto.getCity());
        dbService.deleteCompany(company.get().getTaxId());
    }

}