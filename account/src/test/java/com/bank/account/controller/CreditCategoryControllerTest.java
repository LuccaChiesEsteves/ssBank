package com.bank.account.controller;

import com.bank.account.entity.CreditCategory;
import com.bank.account.service.CreditCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CreditCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    CreditCategoryService creditCategoryService = new CreditCategoryService();

    @Test
    void add_When_ParametersAreCorrect_Then_WillReturnHttpCreated() throws Exception {
        CreditCategory creditCategory = new CreditCategory(null,1, new BigDecimal(0),new BigDecimal(0));

        mockMvc.perform(post("/overdraftCategories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creditCategory)))
                .andExpect(status().isCreated());
    }

    @Test
    void update_When_ParametersAreCorrect_Then_WillReturnHttpOK() throws Exception {
        CreditCategory creditCategory = new CreditCategory("605b4f688b7a82555e3dd707",1, new BigDecimal(0),new BigDecimal(0));

        mockMvc.perform(put("/overdraftCategories/605b4f688b7a82555e3dd707")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creditCategory)))
                .andExpect(status().isOk());
    }

    @Test
    void getId_When_IdIsGiven_Then_WillReturnEntity() throws Exception {
        mockMvc.perform(get("/overdraftCategories/605b4f688b7a82555e3dd707"))
                .andExpect(content().json("{\"id\":\"605b4f688b7a82555e3dd707\",\"requiredScore\":2,\"overdraftValue\":1000}"));
    }
}