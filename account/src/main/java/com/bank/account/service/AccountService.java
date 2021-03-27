package com.bank.account.service;

import com.bank.account.dto.AccountCreditCardDTO;
import com.bank.account.entity.*;
import com.bank.account.enumerator.AccountTypeEnum;
import com.bank.account.enumerator.ClientTypeEnum;
import com.bank.account.repository.AccountRepository;
import com.bank.account.utils.SsBankUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private CreditCategoryService creditCategoryService;

    @Autowired
    private CreditCardService creditCardService;

    public AccountCreditCardDTO createAccount(String clientId){
        AccountCreditCardDTO accountCreditCardDTO = null;
        Optional<Client> client = clientService.findById(clientId);
        if(client.isPresent()){
            CreditCategory creditCategory = getCreditCategory(client.get().getScore());
            Parameter lastAccountNumber = parameterService.findParameterByName("lastAccountNumber");
            Account newAccount = populateAccount(client.get(),lastAccountNumber,creditCategory);
            lastAccountNumber.setValue(newAccount.getAccountNumber());
            parameterService.save(lastAccountNumber);
            accountRepository.save(newAccount);
            accountCreditCardDTO = new AccountCreditCardDTO(newAccount, new ArrayList<>());
            if( creditCategory.getCreditCardValue().compareTo(BigDecimal.ZERO) == 1) {
                CreditCard creditCard = new CreditCard(null, newAccount, creditCategory.getCreditCardValue());
                accountCreditCardDTO.getCreditCards().add(creditCardService.addCreditCard(creditCard));
            }
        }
        return accountCreditCardDTO;
    }

    public Account populateAccount(Client client, Parameter lastAccountNumber, CreditCategory creditCategory){
        String accountNumber = generateNextAccountNumber(lastAccountNumber.getValue());
        String agencyNumber = environment.getProperty("agency.number");
        AccountTypeEnum accountType = client.getClientType().equals(ClientTypeEnum.PF) ? AccountTypeEnum.C : AccountTypeEnum.E;
        BigDecimal overdraft = creditCategory.getOverdraftValue();
        return new Account(null, accountNumber, agencyNumber, accountType, overdraft, client);
    }

    public String generateNextAccountNumber(String lastAccountNumber) {
        Integer numberIncrement = Integer.parseInt(lastAccountNumber);
        numberIncrement++;
        String str = numberIncrement.toString();
        return String.format("%6s", str).replace(' ', '0');
    }

    public CreditCategory getCreditCategory(Integer score){
        CreditCategory creditCategory = creditCategoryService.findByClientScore(score);
        return SsBankUtils.isEmpty(creditCategory) ? new CreditCategory(null,0,BigDecimal.ZERO,BigDecimal.ZERO) : creditCategory;
    }

    public ResponseEntity<List<AccountCreditCardDTO>> getAccountsAndCards(){
        List<Account> accountList = accountRepository.findAll();
        List<AccountCreditCardDTO> accountCreditCardList = new ArrayList<>();
        /*
            This lambda expression was used to simplify the return using mongodb, in a real scenario this could lead to performance issues
            In those cases it would be better to use sql db that is more suitable to inner joins like this
         */
        accountList.forEach(account -> accountCreditCardList.add(new AccountCreditCardDTO( account , creditCardService.findByAccountId(account))));
        return new ResponseEntity<>(accountCreditCardList, HttpStatus.OK);
    }
}