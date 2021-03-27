package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCategory {
    @Id
    private String id;
    @NotNull
    private Integer requiredScore;
    @NotNull
    private BigDecimal overdraftValue;
    @NotNull
    private BigDecimal creditCardValue;
}