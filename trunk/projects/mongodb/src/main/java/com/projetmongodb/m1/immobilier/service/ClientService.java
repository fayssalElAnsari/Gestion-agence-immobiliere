package com.projetmongodb.m1.immobilier.service;

import com.github.javafaker.Faker;
import com.projetmongodb.m1.immobilier.model.Client;
import com.projetmongodb.m1.immobilier.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }

    public Client createMockClient() {
        Faker faker = new Faker(new Locale("en-US"));

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
