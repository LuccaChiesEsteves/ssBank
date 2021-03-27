package com.bank.account.repository;

import com.bank.account.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client,String> {
    Client findClientByDocumentNumber(String documentNumber);
}
