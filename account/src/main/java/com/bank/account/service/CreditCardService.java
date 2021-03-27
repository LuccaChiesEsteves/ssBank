package com.bank.account.service;

import com.bank.account.entity.Account;
import com.bank.account.entity.CreditCard;
import com.bank.account.repository.CreditCardRepository;
import com.bank.account.utils.SsBankUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCard addCreditCard(CreditCard creditCard){
        if (validateFields(creditCard) && !creditCard.getLimit().equals(BigDecimal.ZERO)) {
            creditCardRepository.save(creditCard);
            return creditCard;
        }
        return null;
    }

    private boolean validateFields(CreditCard creditCard){
        return !SsBankUtils.isEmpty(creditCard) && !SsBankUtils.isEmpty(creditCard.getLimit()) && !SsBankUtils.isEmpty(creditCard.getAccountId());
    }

    public ResponseEntity<List<CreditCard>> getCreditCards(){
        return new ResponseEntity<>(creditCardRepository.findAll(), HttpStatus.OK);
    }

    public List<CreditCard> findByAccountId(Account accountId){
        return creditCardRepository.findByAccountId(accountId);
    }
}