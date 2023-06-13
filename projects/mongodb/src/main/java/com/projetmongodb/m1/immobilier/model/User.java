package com.projetmongodb.m1.immobilier.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
}
