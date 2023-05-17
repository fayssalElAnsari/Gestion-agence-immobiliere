package com.projetmongodb.m1.immobilier.repository;

import com.projetmongodb.m1.immobilier.model.Apartment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApartmentRepository extends MongoRepository<Apartment, String> {

}

