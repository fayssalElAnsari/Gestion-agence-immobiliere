package com.projetmongodb.m1.immobilier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clients")
@Data
public class Client {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String countryOfOrigin;
    private boolean isVerified;
    private String profilePicture;

    private List<String> reservations;
    private List<String> transactions;
}
