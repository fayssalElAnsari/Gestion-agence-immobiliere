package com.projetmongodb.m1.immobilier.controller;

import com.projetmongodb.m1.immobilier.model.Apartment;
import com.projetmongodb.m1.immobilier.model.Client;
import com.projetmongodb.m1.immobilier.model.Transaction;
import com.projetmongodb.m1.immobilier.model.User;
import com.projetmongodb.m1.immobilier.service.ApartmentService;
import com.projetmongodb.m1.immobilier.service.ClientService;
import com.projetmongodb.m1.immobilier.service.TransactionService;
import com.projetmongodb.m1.immobilier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mockdata")
public class MockDataController {

    private final ApartmentService apartmentService;
    private final UserService userService;
    private final ClientService clientService;
    private final TransactionService transactionService;

    @Autowired
    public MockDataController(ApartmentService apartmentService, UserService userService,
                              ClientService clientService, TransactionService transactionService) {
        this.apartmentService = apartmentService;
        this.userService = userService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @PostMapping("/apartments")
    public ResponseEntity<String> populateApartments(@RequestBody Map<String, Integer> body) {
        int numberOfDocuments = body.get("numberOfDocuments");
        for (int i = 0; i < numberOfDocuments; i++) {
            Apartment apartment = apartmentService.createMockApartment();
            apartmentService.saveApartment(apartment);
        }
        return new ResponseEntity<>("Mock data for apartments created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/users")
    public ResponseEntity<String> populateUsers(@RequestBody Map<String, Integer> body) {
        int numberOfDocuments = body.get("numberOfDocuments");
        for (int i = 0; i < numberOfDocuments; i++) {
            User user = userService.createMockUser();
            userService.saveUser(user);
        }
        return new ResponseEntity<>("Mock data for users created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/clients")
    public ResponseEntity<String> populateClients(@RequestBody Map<String, Integer> body) {
        int numberOfDocuments = body.get("numberOfDocuments");
        for (int i = 0; i < numberOfDocuments; i++) {
            Client client = clientService.createMockClient();
            clientService.saveClient(client);
        }
        return new ResponseEntity<>("Mock data for clients created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> populateTransactions(@RequestBody Map<String, Integer> body) {
        int numberOfDocuments = body.get("numberOfDocuments");
        for (int i = 0; i < numberOfDocuments; i++) {
            Transaction transaction = transactionService.createMockTransaction();
            transactionService.saveTransaction(transaction);
        }
        return new ResponseEntity<>("Mock data for transactions created successfully", HttpStatus.CREATED);
    }

}

