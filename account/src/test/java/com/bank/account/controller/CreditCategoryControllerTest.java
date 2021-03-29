package com.bank.account.controller;

import com.bank.account.entity.CreditCategory;
import com.bank.account.repository.CreditCategoryRepository;
import com.bank.account.service.CreditCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
class CreditCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class CreditCategoryServiceTestContextConfiguration {
        @Bean
        public CreditCategoryService creditCategoryService() {
            return new CreditCategoryService();
        }
    }

    @Autowired
    CreditCategoryService creditCategoryService;

    @MockBean
    private CreditCategoryRepository creditCategoryRepository;

    @Test
    void add_When_ParametersAreCorrect_Then_WillReturnHttpCreated() throws Exception {
        CreditCategory creditCategory = new CreditCategory(null,1, new BigDecimal(0),new BigDecimal(0));
        CreditCategory creditCategorySaved = new CreditCategory("savedTest",1, new BigDecimal(0),new BigDecimal(0));
        when(creditCategoryRepository.save(creditCategory)).thenReturn(creditCategorySaved);
        mockMvc.perform(post("/creditCategories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creditCategory)))
                .andExpect(status().isCreated());
    }

    @Test
    void update_When_ParametersAreCorrect_Then_WillReturnHttpOK() throws Exception {
        Optional<CreditCategory> creditCategory = Optional.of(new CreditCategory("testId",1, new BigDecimal(0),new BigDecimal(0)));
        when(creditCategoryRepository.findById("testId")).thenReturn(creditCategory);
        when(creditCategoryRepository.save(any(CreditCategory.class))).thenAnswer(i -> i.getArguments()[0]);
        mockMvc.perform(put("/creditCategories/testId")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creditCategory.get())))
                .andExpect(status().isOk());
    }

}