package com.raymundo.bankapp.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "CREDIT")
@Table(name = "CREDIT")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "credit_limit")
    private double creditLimit;

    @Column(name = "interest_rate")
    private double interestRate;

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
