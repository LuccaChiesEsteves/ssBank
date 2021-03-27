package com.bank.account.service;

import com.bank.account.entity.Parameter;
import com.bank.account.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    public Parameter findParameterByName(String name){
        return parameterRepository.findParameterByName(name);
    }

    public Parameter save(Parameter parameter){
        return parameterRepository.save(parameter);
    }
}
