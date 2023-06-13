package com.projetmongodb.m1.immobilier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
public class Transaction {

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REVERSED
    }

    public enum TransactionType {
        DEBIT,
        CREDIT
    }

    public enum TransactionMode {
        CARD,
        NET_BANKING,
        UPI
    }

    @Id
    private String id;

    private String clientId;
    private String reservationId;
    private double amount;
    private String currency;
    private String bankName;
    private String accountNumber;
    private TransactionType transactionType;
    private TransactionMode transactionMode;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDateTime;
}

