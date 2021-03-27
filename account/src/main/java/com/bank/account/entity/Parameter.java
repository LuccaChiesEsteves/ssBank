package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Parameter {
    @Id
    private String id;
    private String name;
    private String value;
}