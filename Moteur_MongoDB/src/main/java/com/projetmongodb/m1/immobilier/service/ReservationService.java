package com.projetmongodb.m1.immobilier.service;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.Reservation;
import com.projetmongodb.m1.immobilier.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(String id, Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        existingReservation.setApartmentId(updatedReservation.getApartmentId());
        existingReservation.setUserId(updatedReservation.getUserId());
        existingReservation.setStartDate(updatedReservation.getStartDate());
        existingReservation.setEndDate(updatedReservation.getEndDate());
        existingReservation.setPrice(updatedReservation.getPrice());

        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(String id) {
        reservationRepository.deleteById(id);
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public Reservation createMockReservation() {
        Faker faker = new Faker(new Locale("en-US")); // Adjust Locale as needed

        Reservation reservation = new Reservation();
        reservation.setApartmentId(faker.random().hex());
        reservation.setUserId(faker.random().hex());
        reservation.setStartDate(faker.date().future(30, TimeUnit.DAYS));
        reservation.setEndDate(faker.date().future(60, TimeUnit.DAYS));
        reservation.setPrice(faker.number().randomDouble(2, 50, 2000));

        return reservationRepository.save(reservation);
    }
}

