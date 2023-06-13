package com.projetmongodb.m1.immobilier.service;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.User;
import com.projetmongodb.m1.immobilier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createMockUser() {
        Faker faker = new Faker(new Locale("en-US"));
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

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
