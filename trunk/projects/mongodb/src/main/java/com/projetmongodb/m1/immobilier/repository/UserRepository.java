package com.projetmongodb.m1.immobilier.repository;

import com.projetmongodb.m1.immobilier.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}