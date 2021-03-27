package com.bank.account.enumerator;

public enum ResponseMessageEnum {
    M100("message.ok"),
    M101("message.clientCreated"),
    M102("message.creditCategoryCreated"),
    M103("message.registryUpdated"),
    M200("message.missingParameters"),
    M201("message.invalidName"),
    M202("message.invalidScore"),
    M203("message.invalidDocument"),
    M204("message.docAlreadyRegistered"),
    M205("message.idNotFound");

    private String description;

    ResponseMessageEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
