package com.bank.account.service;

import com.bank.account.dto.AccountCreditCardDTO;
import com.bank.account.entity.*;
import com.bank.account.enumerator.AccountTypeEnum;
import com.bank.account.enumerator.ClientTypeEnum;
import com.bank.account.repository.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class AccountServiceTest {

    @TestConfiguration
    static class AccountServiceTestContextConfiguration {
        @Bean
        public AccountService accountService() {
            return new AccountService();
        }
    }

    @Autowired
    private AccountService accountService;

    @Autowired
    private Environment environment;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ParameterRepository parameterRepository;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @Before
    public void setUp() {
        when(parameterRepository.save(any(Parameter.class))).thenAnswer(i -> i.getArguments()[0]);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
        when(creditCardRepository.save(any(CreditCard.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void generateNextAccountNumber_When_CorrectNumber_Then_ReturnNextNumberFormatted() {
        assertEquals(accountService.generateNextAccountNumber("099999"), "100000");
        assertEquals(accountService.generateNextAccountNumber("000000"),"000001");
    }

    @Test
    void populateAccount_When_ParametersSent_Then_WillReturnNewAccountPopulated(){
        Client client = new Client("testId", "Client Name", ClientTypeEnum.PJ,"12345678901234", 4);
        Parameter lastAccountNumber = new Parameter(null, "lastAccountNumber", "000009");
        CreditCategory creditCategory = new CreditCategory(null, 2, new BigDecimal(1000), new BigDecimal(220));
        Account expectedAccount = new Account(null, "000010", "8008", AccountTypeEnum.E, creditCategory.getOverdraftValue(), client);
        assertEquals(accountService.populateAccount(client, lastAccountNumber, creditCategory),expectedAccount);
    }

    @Test
    void createAccount_When_ClientScoreMoreThanOne_Then_ReturnAccountAndCreditCard(){
        AccountCreditCardDTO expectedResult = createAccountCreditCardDTO(ClientTypeEnum.PF, 5, "12345678901", "000001", AccountTypeEnum.C, new BigDecimal(1000), new BigDecimal(220));
        Optional<Client> client = Optional.of( expectedResult.getAccount().getClientId());
        when(clientRepository.findById(any(String.class))).thenReturn(client);
        Parameter lastAccountNumber = new Parameter("testId", "lastAccountNumber", "000000");
        when(parameterRepository.findParameterByName("lastAccountNumber")).thenReturn(lastAccountNumber);
        assertEquals(accountService.createAccount("12345678901"),expectedResult);
    }


    @Test
    void createAccount_When_ClientScoreMoreIsOne_Then_ReturnOnlyAccount(){
        AccountCreditCardDTO expectedResult = createAccountCreditCardDTO(ClientTypeEnum.PF, 1, "12345678901", "000001", AccountTypeEnum.C, new BigDecimal(0), new BigDecimal(0));
        Optional<Client> client = Optional.of( expectedResult.getAccount().getClientId());
        when(clientRepository.findById(any(String.class))).thenReturn(client);
        Parameter lastAccountNumber = new Parameter("testId", "lastAccountNumber", "000000");
        when(parameterRepository.findParameterByName("lastAccountNumber")).thenReturn(lastAccountNumber);
        assertEquals(accountService.createAccount("12345678901"),expectedResult);
    }

    private AccountCreditCardDTO createAccountCreditCardDTO(ClientTypeEnum clientType, int score, String documentNumber, String accountNumber, AccountTypeEnum accountType, BigDecimal overdraftValue, BigDecimal creditCardLimit){
        Client client = new Client("testId","Client Name", clientType,documentNumber,score);
        Account account = new Account(null, accountNumber, environment.getProperty("agency.number"), accountType, overdraftValue, client);
        CreditCard creditCard = new CreditCard(null, account, creditCardLimit);
        List<CreditCard>creditCardList = new ArrayList<>();
        if(!creditCard.getLimit().equals(BigDecimal.ZERO) ){
            creditCardList.add(creditCard);
        }
        return new AccountCreditCardDTO(account, creditCardList);
    }
}