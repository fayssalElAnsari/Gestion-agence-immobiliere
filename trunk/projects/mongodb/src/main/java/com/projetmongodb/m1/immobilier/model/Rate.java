package com.projetmongodb.m1.immobilier.model;

import lombok.Data;

import java.util.Date;

@Data
public class Rate {
    private Date startDate;
    private Date endDate;
    private double price;
}
