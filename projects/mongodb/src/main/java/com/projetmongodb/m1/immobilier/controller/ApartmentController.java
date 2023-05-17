package com.projetmongodb.m1.immobilier.controller;

import com.projetmongodb.m1.immobilier.model.Apartment;
import com.projetmongodb.m1.immobilier.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public ResponseEntity<List<Apartment>> getAllApartments() {
        return ResponseEntity.ok(apartmentService.getAllApartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getApartmentById(@PathVariable String id) {
        return ResponseEntity.ok(apartmentService.getApartmentById(id));
    }

    @PostMapping
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) {
        return ResponseEntity.ok(apartmentService.createApartment(apartment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apartment> updateApartment(@PathVariable String id, @RequestBody Apartment apartment) {
        return ResponseEntity.ok(apartmentService.updateApartment(id, apartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable String id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok().build();
    }
}