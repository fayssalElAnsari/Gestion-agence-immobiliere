package com.projetmongodb.m1.immobilier.controller;

import com.projetmongodb.m1.immobilier.model.Client;
import com.projetmongodb.m1.immobilier.service.ClientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Iterable<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Optional<Client> getClient(@PathVariable String id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client updatedClient) {
        Client currentClient = clientService.getClientById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        currentClient.setName(updatedClient.getName());
        currentClient.setEmail(updatedClient.getEmail());
        return clientService.saveClient(currentClient);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
    }
}

