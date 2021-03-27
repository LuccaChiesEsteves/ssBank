package com.bank.account.controller;

import com.bank.account.entity.CreditCard;
import com.bank.account.service.CreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Credit cards")
@RestController
@RequestMapping("/creditCards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @ApiOperation(value = "Return a list containing all credit cards")
    @GetMapping
    public ResponseEntity<List<CreditCard>> getCreditCards() {
        return creditCardService.getCreditCards();
    }
}
