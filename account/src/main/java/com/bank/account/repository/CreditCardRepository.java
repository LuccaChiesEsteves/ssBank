package com.bank.account.repository;

import com.bank.account.entity.Account;
import com.bank.account.entity.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {
    List<CreditCard> findByAccountId(Account accountId);
}
