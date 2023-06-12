package com.projetmongodb.m1.immobilier.controller;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.Apartment;
import com.projetmongodb.m1.immobilier.model.User;
import com.projetmongodb.m1.immobilier.repository.ApartmentRepository;
import com.projetmongodb.m1.immobilier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mockdata")
public class MockDataController {

    @Autowired
    private ApartmentRepository apartmentRepository;  // Assuming you have a repository for Apartment

    @Autowired
    private UserRepository userRepository;

    private final Faker faker = new Faker();

    @PostMapping("/{collection}")
    public ResponseEntity<String> populateDatabase(@PathVariable String collection, @RequestParam int numberOfDocuments) {
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


    private Apartment createMockApartment() {
        Apartment apartment = new Apartment();

        // Generate mock data with JavaFaker
        apartment.setId(faker.idNumber().valid());
        apartment.setTitle(faker.address().streetName());
        apartment.setDescription(faker.lorem().sentence());
        apartment.setDefaultPrice(faker.number().randomDouble(2, 500, 2000));
        apartment.setNumberOfRooms(faker.number().numberBetween(1, 5));
        apartment.setNumberOfBathrooms(faker.number().numberBetween(1, 3));
        apartment.setArea(faker.number().randomDouble(2, 20, 200));
        apartment.setAddress(faker.address().fullAddress());
        // add other fields

        return apartment;
    }
}

