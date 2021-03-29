package com.bank.account.controller;

import com.bank.account.entity.CreditCategory;
import com.bank.account.service.CreditCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(value = "Credit categories")
@RestController
@RequestMapping("/creditCategories")
public class CreditCategoryController {

    @Autowired
    CreditCategoryService creditCategoryService;

    @ApiOperation(value = "Return a list containing all credit categories")
    @GetMapping
    public ResponseEntity<List<CreditCategory>> get(){
        return creditCategoryService.getCreditCategories();
    }

    @ApiOperation(value = "Return a specific credit category")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditCategory> getId(@NotEmpty @PathVariable String id){
        return creditCategoryService.getCreditCategoryById(id);
    }

    @ApiOperation(value = "Create a new credit category")
    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody CreditCategory creditCategory){
        return creditCategoryService.addCreditCategory(creditCategory);
    }

    @ApiOperation(value = "Update a specific credit category")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody CreditCategory creditCategory, @NotEmpty @PathVariable String id) {
        return creditCategoryService.updateCreditCategory(creditCategory,id);
    }
}