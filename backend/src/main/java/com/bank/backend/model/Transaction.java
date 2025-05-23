package com.bank.backend.model;

import com.bank.backend.constant.TransactionMode;
import com.bank.backend.model.helper.Auditing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionMode mode;
    private LocalDateTime timestamp;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_sl_no")
    private  Account account;
}
