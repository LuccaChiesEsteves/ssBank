package com.bank.account.service;

import com.bank.account.entity.CreditCategory;
import com.bank.account.repository.CreditCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCategoryService {

    @Autowired
    private CreditCategoryRepository creditCategoryRepository;

    @Autowired
    private Environment environment;

    public ResponseEntity<String> addCreditCategory(CreditCategory creditCategory) {
        creditCategoryRepository.save(creditCategory);
        return new ResponseEntity<>(environment.getProperty("message.creditCategoryCreated"), HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateCreditCategory(CreditCategory creditCategory, String id) {
        Optional<CreditCategory> validateId = creditCategoryRepository.findById(creditCategory.getId());
        if (validateId.isEmpty()) {
            return new ResponseEntity<>(environment.getProperty("message.idNotFound"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        creditCategoryRepository.save(creditCategory);
        return new ResponseEntity<>(environment.getProperty("message.registryUpdated"), HttpStatus.OK);
    }

    public ResponseEntity<List<CreditCategory>> getCreditCategories() {
        return new ResponseEntity<>(creditCategoryRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<CreditCategory> getCreditCategoryById(String id) {
        Optional<CreditCategory> overdraftCategory = creditCategoryRepository.findById(id);
        return new ResponseEntity<>(overdraftCategory.get(), HttpStatus.OK);
    }

    public CreditCategory findByClientScore(Integer score) {
        List<CreditCategory> creditCategoryList = creditCategoryRepository.findByRequiredScoreBetween(0, score + 1);
        if (!creditCategoryList.isEmpty()) {
            creditCategoryList.sort(Comparator.comparingInt(CreditCategory::getRequiredScore).reversed());
            return creditCategoryList.get(0);
        }
        return null;
    }
}
