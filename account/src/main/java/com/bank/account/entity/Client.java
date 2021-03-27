package com.bank.account.entity;

import com.bank.account.enumerator.ClientTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Client {
    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotNull
    private ClientTypeEnum clientType;
    @NotEmpty
    private String documentNumber;
    @NotNull
    private Integer score;
}