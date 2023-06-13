package com.projetmongodb.m1.immobilier.controller;

import com.projetmongodb.m1.immobilier.model.Apartment;
import com.projetmongodb.m1.immobilier.model.Reservation;
import com.projetmongodb.m1.immobilier.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllApartments() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping("/mock")
    public Reservation createMockReservation() {
        return reservationService.createMockReservation();
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable String id, @RequestBody Reservation updatedReservation) {
        return reservationService.updateReservation(id, updatedReservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
    }

    // Add other endpoints as needed
}

