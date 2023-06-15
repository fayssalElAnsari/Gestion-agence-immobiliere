package com.projetmongodb.m1.immobilier.service;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.Apartment;
import com.projetmongodb.m1.immobilier.model.Location;
import com.projetmongodb.m1.immobilier.model.Rate;
import com.projetmongodb.m1.immobilier.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        apartment.setCreatedAt(new Date());
        apartment.setUpdatedAt(new Date());
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
                    apartment.setUpdatedAt(new Date());
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

    public Apartment createMockApartment() {
        Faker faker = new Faker(new Locale("en-US"));

        Apartment apartment = new Apartment();
        apartment.setTitle(faker.lorem().word());
        apartment.setDescription(faker.lorem().sentence());
        apartment.setDefaultPrice(faker.number().randomDouble(2, 100, 1000));
        apartment.setNumberOfRooms(faker.number().numberBetween(1, 5));
        apartment.setNumberOfBathrooms(faker.number().numberBetween(1, 3));
        apartment.setArea(faker.number().randomDouble(2, 30, 200));
        apartment.setAddress(faker.address().fullAddress());

        // Assuming Location is a class with latitude and longitude fields
        Location location = new Location();
        location.setLatitude(faker.number().randomDouble(5, -90, 90));
        location.setLongitude(faker.number().randomDouble(5, -180, 180));
        apartment.setLocation(location);

        apartment.setOwnerId(faker.idNumber().valid());

        // Assuming you just need a list of random image URLs
        List<String> images = IntStream.range(0, 5)
                .mapToObj(i -> faker.internet().image())
                .collect(Collectors.toList());
        apartment.setImages(images);

        // TODO: setup rates
        // Assuming Rate is a class with startDate, endDate, and price fields
        List<Rate> rates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Rate rate = new Rate();
            rate.setStartDate(faker.date().past(30, TimeUnit.DAYS));
            rate.setEndDate(faker.date().future(30, TimeUnit.DAYS));
            rate.setPrice(faker.number().randomDouble(2, 50, 200));
            rates.add(rate);
        }
        apartment.setRates(rates);

        apartment.setCreatedAt(new Date());
        apartment.setUpdatedAt(new Date());

        // TODO: setup reservations
        // Assuming Reservation is a class with necessary fields
//        List<Reservation> reservations = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Reservation reservation = new Reservation();
//            // Set reservation fields here
//            reservations.add(reservation);
//        }
//        apartment.setReservations(reservations);

        return apartment;
    }

    public void saveApartment(Apartment apartment) {
        apartmentRepository.save(apartment);
    }
}
