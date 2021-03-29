package com.bank.account.service;

import com.bank.account.entity.Client;
import com.bank.account.enumerator.ClientTypeEnum;
import com.bank.account.enumerator.ResponseMessageEnum;
import com.bank.account.repository.ClientRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class ClientServiceTest {

    @TestConfiguration
    static class ClientServiceTestContextConfiguration {
        @Bean
        public ClientService clientService() {
            return new ClientService() {
            };
        }
    }

    @Autowired
    private ClientService clientService;

    @Autowired
    private Environment environment;

    @MockBean
    private ClientRepository clientRepository;

    @Before
    public void setUp() {
        Client client = new Client("test","Pedro Pedroso", ClientTypeEnum.PF,"12345678910",5);
        Mockito.when(clientRepository.save(client)).thenReturn(client);
    }

    @Test
    void addClient_When_ParametersAreCorrect_Then_WillSave(){
        Client client = new Client("test","Pedro Pedroso", ClientTypeEnum.PF,"12345678910",5);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>(environment.getProperty("message.clientCreated"), HttpStatus.CREATED);
        assertEquals(clientService.addClient(client), expectedResponse);
    }

    @Test
    void validateFields_When_AllFieldsCorrect_Then_OkReturned() {
        Client client = new Client(null, "José Silva", ClientTypeEnum.PJ, "12345678901234", 5);
        assertEquals(clientService.validateFields(client), ResponseMessageEnum.M100);
    }

    @Test
    void validateFields_When_DocumentTypeIncorrect_Then_InvalidDocumentReturned() {
        Client clientPJ = new Client(null, "Carlos Silva", ClientTypeEnum.PJ, "12345678902", 6);
        Client clientPF = new Client(null, "Carlos Silva", ClientTypeEnum.PF, "12345678901234", 6);
        Client clientLetter = new Client(null, "Carlos Silva", ClientTypeEnum.PJ, "12345AIUUJHH", 6);
        assertAll(() -> assertEquals(clientService.validateFields(clientPJ), ResponseMessageEnum.M203),
                () -> assertEquals(clientService.validateFields(clientPF), ResponseMessageEnum.M203),
                () -> assertEquals(clientService.validateFields(clientLetter), ResponseMessageEnum.M203));
    }
}