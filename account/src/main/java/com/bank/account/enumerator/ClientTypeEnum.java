package com.bank.account.enumerator;

public enum ClientTypeEnum {
    PF("Pessoa física"),
    PJ("Pessoa jurídica");

    private String description;

    ClientTypeEnum(String description) {
        this.description = description;
    }

    private String getDescription() {
        return description;
    }
}