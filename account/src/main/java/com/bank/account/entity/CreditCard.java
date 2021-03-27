package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreditCard {
    @Id
    private String id;
    @NotNull
    private Account accountId;
    @NotNull
    private BigDecimal limit;
}