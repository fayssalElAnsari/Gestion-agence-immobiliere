package com.projetmongodb.m1.immobilier.model;

import lombok.Data;

import java.util.Date;

@Data
public class Reservation {
    private String id;
    private String apartmentId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private double price;

}
