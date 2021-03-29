package com.bank.account.controller;

import com.bank.account.entity.Client;
import com.bank.account.enumerator.ClientTypeEnum;
import com.bank.account.repository.ClientRepository;
import com.bank.account.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class ClientServiceTestContextConfiguration {
        @Bean
        public ClientService clientService() {
            return new ClientService();
        }
    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        when(clientRepository.save(any(Client.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void add_When_ParametersAreCorrect_Then_WillReturnHTTPCreated() throws Exception {
        Client client = new Client("testParCor","Pedro Pedroso", ClientTypeEnum.PF,"00000000000",5);
        mockMvc.perform(post("/clients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());
    }
}