package com.projetmongodb.m1.immobilier.controller;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.*;
import com.projetmongodb.m1.immobilier.repository.ApartmentRepository;
import com.projetmongodb.m1.immobilier.repository.ClientRepository;
import com.projetmongodb.m1.immobilier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/mockdata")
public class MockDataController {

    @Autowired
    private ApartmentRepository apartmentRepository;  // Assuming you have a repository for Apartment

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    private final Faker faker = new Faker();

    @PostMapping("/{collection}")
    public ResponseEntity<String> populateDatabase(@PathVariable String collection, @RequestBody Map<String, Integer> body) {
        int numberOfDocuments = body.get("numberOfDocuments");
        if (collection.equalsIgnoreCase("apartments")) {
            for (int i = 0; i < numberOfDocuments; i++) {
                Apartment apartment = createMockApartment();
                apartmentRepository.save(apartment);
            }
        } else if (collection.equalsIgnoreCase("users")) {
            for (int i = 0; i < numberOfDocuments; i++) {
                User user = createMockUser();
                userRepository.save(user);
            }
        } else if (collection.equalsIgnoreCase("clients")) {
            for (int i = 0; i < numberOfDocuments; i++) {
                Client client = createMockClient();
                clientRepository.save(client);
            }
        } else {
            return new ResponseEntity<>("Invalid collection name", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Mock data created successfully", HttpStatus.CREATED);
    }


    private User createMockUser() {
        User user = new User();

        // Generate unique data with JavaFaker
        String uniqueSuffix = String.valueOf(faker.number().randomNumber()); // Creates a large unique random number
        String name = faker.name().fullName();
        String email = "user" + uniqueSuffix + "@example.com"; // Mock email with unique suffix

        user.setName(name);
        user.setEmail(email);
        // Hashes and salts a generated password
        user.setPassword(faker.internet().password());

        return user;
    }


    public Apartment createMockApartment() {
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

    public Client createMockClient() {
        Client client = new Client();

        client.setEmail(faker.internet().emailAddress());
        client.setPassword(faker.internet().password()); // Remember to hash password in real-world scenario
        client.setPhoneNumber(faker.phoneNumber().phoneNumber());
        client.setCountryOfOrigin(faker.address().country());
        client.setVerified(faker.bool().bool());
        client.setProfilePicture(faker.internet().image());
        client.setReservations(Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid()));
        client.setTransactions(Arrays.asList(faker.idNumber().valid(), faker.idNumber().valid()));

        return clientRepository.save(client);
    }
}

