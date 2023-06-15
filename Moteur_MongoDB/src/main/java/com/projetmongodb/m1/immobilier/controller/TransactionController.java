package com.projetmongodb.m1.immobilier.controller;

import com.projetmongodb.m1.immobilier.model.Transaction;
import com.projetmongodb.m1.immobilier.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public Iterable<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable String id, @RequestBody Transaction updatedTransaction) {
        Transaction existingTransaction = transactionService.getTransactionById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Update the fields of the existing transaction with the values from the updatedTransaction
        existingTransaction.setClientId(updatedTransaction.getClientId());
        existingTransaction.setReservationId(updatedTransaction.getReservationId());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setCurrency(updatedTransaction.getCurrency());
        existingTransaction.setBankName(updatedTransaction.getBankName());
        existingTransaction.setAccountNumber(updatedTransaction.getAccountNumber());
        existingTransaction.setTransactionType(updatedTransaction.getTransactionType());
        existingTransaction.setTransactionMode(updatedTransaction.getTransactionMode());
        existingTransaction.setTransactionStatus(updatedTransaction.getTransactionStatus());
        existingTransaction.setTransactionDateTime(updatedTransaction.getTransactionDateTime());

        return transactionService.saveTransaction(existingTransaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }
}

