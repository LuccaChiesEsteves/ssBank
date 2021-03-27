package com.bank.account.enumerator;

public enum AccountTypeEnum {
    C("Corrente"),
    E("Empresarial");

    private String description;

    AccountTypeEnum(String description) {
        this.description = description;
    }

    private String getDescription() {
        return description;
    }
}