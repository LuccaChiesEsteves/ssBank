package com.bank.account.entity;

import com.bank.account.enumerator.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
    @Id
    private String id;
    private String accountNumber;
    private String agency;
    private AccountTypeEnum accountType;
    private BigDecimal overDraft;
    private Client clientId;
}