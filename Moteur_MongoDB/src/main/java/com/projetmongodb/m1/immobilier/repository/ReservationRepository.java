package com.projetmongodb.m1.immobilier.repository;

import com.projetmongodb.m1.immobilier.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    // Add custom query methods if needed
}

