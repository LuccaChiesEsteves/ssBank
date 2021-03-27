package com.bank.account.repository;

import com.bank.account.entity.Parameter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParameterRepository extends MongoRepository<Parameter,String> {
    Parameter findParameterByName(String name);
}
