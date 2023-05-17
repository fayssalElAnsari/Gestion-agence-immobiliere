package com.projetmongodb.m1.immobilier.service;

import com.projetmongodb.m1.immobilier.model.Apartment;
import com.projetmongodb.m1.immobilier.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment getApartmentById(String id) {
        return apartmentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Apartment with id " + id + " not found.")
        );
    }

    public Apartment createApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(String id, Apartment newApartment) {
        return apartmentRepository.findById(id)
                .map(apartment -> {
                    apartment.setTitle(newApartment.getTitle());
                    apartment.setDescription(newApartment.getDescription());
                    apartment.setDefaultPrice(newApartment.getDefaultPrice());
                    apartment.setNumberOfRooms(newApartment.getNumberOfRooms());
                    apartment.setNumberOfBathrooms(newApartment.getNumberOfBathrooms());
                    apartment.setArea(newApartment.getArea());
                    apartment.setAddress(newApartment.getAddress());
                    apartment.setLocation(newApartment.getLocation());
                    apartment.setOwnerId(newApartment.getOwnerId());
                    apartment.setImages(newApartment.getImages());
                    apartment.setRates(newApartment.getRates());
                    return apartmentRepository.save(apartment);
                })
                .orElseThrow(
                        () -> new IllegalArgumentException("Apartment with id " + id + " not found.")
                );
    }

    public void deleteApartment(String id) {
        if(apartmentRepository.existsById(id)) {
            apartmentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Apartment with id " + id + " not found.");
        }
    }
}
