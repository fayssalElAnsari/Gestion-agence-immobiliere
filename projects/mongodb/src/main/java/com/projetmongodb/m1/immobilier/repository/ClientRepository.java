package com.projetmongodb.m1.immobilier.repository;

import com.projetmongodb.m1.immobilier.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
}

