package com.projetmongodb.m1.immobilier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
@Data
@Document(collection = "apartments")
public class Apartment {

    @Id
    private String id;

    private String title;
    private String description;
    private double defaultPrice;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private double area;
    private String address;
    private Location location;
    private String ownerId;
    private List<String> images;
    private List<Rate> rates;
    private Date createdAt;
    private Date updatedAt;
    private List<Reservation> reservations;

}