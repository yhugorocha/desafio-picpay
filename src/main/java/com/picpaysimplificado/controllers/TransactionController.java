package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domains.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction){
        Transaction newTransaction = service.createTransaction(transaction);
        return new ResponseEntity(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = service.getAllTransactions();
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }
}
