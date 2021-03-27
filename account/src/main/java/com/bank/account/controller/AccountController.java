package com.bank.account.controller;

import com.bank.account.dto.AccountCreditCardDTO;
import com.bank.account.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Accounts")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value ="Return a list containing all accounts and their credit cards")
    @GetMapping
    public ResponseEntity<List<AccountCreditCardDTO>>getAccountsAndCards(){
        return accountService.getAccountsAndCards();
    }
}