package com.projetmongodb.m1.immobilier.repository;

import com.projetmongodb.m1.immobilier.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // additional queries if needed
}