package com.raymundo.bankapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreditDto extends BaseDto {

    private UUID uuid;

    @NotNull
    @Min(1)
    private double creditLimit;

    @NotNull
    @Min(1)
    private double interestRate;

    public CreditDto(double creditLimit, double interestRate) {
        this.uuid = UUID.randomUUID();
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditDto(UUID uuid, double creditLimit, double interestRate) {
        this.uuid = uuid;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditDto() {

    }

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
