package com.bank.account.listener;

import com.bank.account.dto.AccountCreditCardDTO;
import com.bank.account.service.AccountService;
import com.bank.account.utils.SsBankUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMessageListener {

    @Autowired
    private AccountService accountService;

    private Logger logger = LoggerFactory.getLogger(AccountMessageListener.class);

    public void receiveMessage(String message){
        logger.info("Trying to create account for client {}", message);
        AccountCreditCardDTO accountCreditCardDTO = accountService.createAccount(message);
        if (SsBankUtils.isEmpty(accountCreditCardDTO)){
            logger.error("Error while creating account for client {}", message);
        } else {
            logger.info("Account created for client {}",message);
        }
    }
}