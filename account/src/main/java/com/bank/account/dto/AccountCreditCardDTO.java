package com.bank.account.dto;

import com.bank.account.entity.Account;
import com.bank.account.entity.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreditCardDTO {
    private Account account;
    private List<CreditCard> creditCards;
}