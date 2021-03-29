package com.bank.account.service;

import com.bank.account.entity.Account;
import com.bank.account.entity.Client;
import com.bank.account.entity.CreditCard;
import com.bank.account.enumerator.AccountTypeEnum;
import com.bank.account.enumerator.ClientTypeEnum;
import com.bank.account.repository.CreditCardRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class CreditCardServiceTest {

    @TestConfiguration
    static class CreditCardServiceTestContextConfiguration {
        @Bean
        public CreditCardService creditCardService() {
            return new CreditCardService() {
            };
        }
    }

    @Before
    public void setUp(){
        when(creditCardRepository.save(any(CreditCard.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Autowired
    private CreditCardService creditCardService;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @Test
    void addCreditCard_When_CreditCardLimitIsZero_Then_WillReturnNull() {
        Client client = new Client("testId", "Client Name", ClientTypeEnum.PJ,"12345678901234", 0);
        Account account = new Account(null, "000010", "8008", AccountTypeEnum.E, BigDecimal.ZERO, client);
        CreditCard creditCard = new CreditCard(null, account, BigDecimal.ZERO);
        assertEquals(creditCardService.addCreditCard(creditCard), null);
    }

    @Test
    void addCreditCard_When_CreditCardLimitIsGreaterThanZero_Then_WillReturnCreditCard() {
        Client client = new Client("testId", "Client Name", ClientTypeEnum.PJ,"12345678901234", 4);
        Account account = new Account(null, "000010", "8008", AccountTypeEnum.E, BigDecimal.TEN, client);
        CreditCard creditCard = new CreditCard(null, account, BigDecimal.TEN);
        assertEquals(creditCardService.addCreditCard(creditCard), creditCard);
    }
}