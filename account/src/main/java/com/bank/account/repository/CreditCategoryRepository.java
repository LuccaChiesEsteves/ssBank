package com.bank.account.repository;

import com.bank.account.entity.CreditCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CreditCategoryRepository extends MongoRepository<CreditCategory, String> {
    List<CreditCategory> findByRequiredScoreBetween(Integer from, Integer to);
}
