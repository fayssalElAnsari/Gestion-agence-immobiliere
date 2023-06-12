package com.projetmongodb.m1.immobilier.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
}
