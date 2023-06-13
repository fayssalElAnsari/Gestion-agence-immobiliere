package com.projetmongodb.m1.immobilier.service;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.Transaction;
import com.projetmongodb.m1.immobilier.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TransactionService {
    private final List<String> bankNames = Arrays.asList(
            "Bank of America",
            "JPMorgan Chase",
            "Wells Fargo",
            "Citigroup",
            "Goldman Sachs",
            "Morgan Stanley",
            "HSBC",
            "Barclays",
            "Royal Bank of Canada",
            "BNP Paribas"
    );

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }

    public Transaction createMockTransaction() {
        Faker faker = new Faker(new Locale("en-US")); // Adjust Locale as needed

        Transaction transaction = new Transaction();

        transaction.setClientId(faker.idNumber().valid());
        transaction.setReservationId(faker.idNumber().valid());
        transaction.setAmount(faker.number().randomDouble(2, 1, 10000));
        transaction.setCurrency("USD");
        transaction.setBankName(getRandomBankName());
        transaction.setAccountNumber(faker.finance().iban());
        transaction.setTransactionType(Transaction.TransactionType.values()[faker.number().numberBetween(0, Transaction.TransactionType.values().length)]);
        transaction.setTransactionMode(Transaction.TransactionMode.values()[faker.number().numberBetween(0, Transaction.TransactionMode.values().length)]);
        transaction.setTransactionStatus(Transaction.TransactionStatus.values()[faker.number().numberBetween(0, Transaction.TransactionStatus.values().length)]);
        transaction.setTransactionDateTime(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    private String getRandomBankName() {
        int randomIndex = new Faker().random().nextInt(bankNames.size());
        return bankNames.get(randomIndex);
    }
}

