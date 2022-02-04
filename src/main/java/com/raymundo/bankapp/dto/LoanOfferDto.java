package com.raymundo.bankapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class LoanOfferDto extends BaseDto {

    private UUID uuid;

    @NotNull
    private ClientDto clientDto;

    @NotNull
    private CreditDto creditDto;

    @NotNull
    @Min(1)
    private double creditAmount;

    @NotNull
    private List<PaymentScheduleDto> paymentSchedules;

    public LoanOfferDto(ClientDto clientDto, CreditDto creditDto, double creditAmount, List<PaymentScheduleDto> paymentSchedules) {
        this.uuid = UUID.randomUUID();
        this.clientDto = clientDto;
        this.creditDto = creditDto;
        this.creditAmount = creditAmount;
        this.paymentSchedules = paymentSchedules;
    }

    public LoanOfferDto(UUID uuid, ClientDto clientDto, CreditDto creditDto, double creditAmount, List<PaymentScheduleDto> paymentSchedules) {
        this.uuid = uuid;
        this.clientDto = clientDto;
        this.creditDto = creditDto;
        this.creditAmount = creditAmount;
        this.paymentSchedules = paymentSchedules;
    }

    public LoanOfferDto() {

    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setClient(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public void setCredit(CreditDto creditDto) {
        this.creditDto = creditDto;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public void setPaymentSchedules(List<PaymentScheduleDto> paymentSchedules) {
        this.paymentSchedules = paymentSchedules;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ClientDto getClient() {
        return clientDto;
    }

    public CreditDto getCredit() {
        return creditDto;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public List<PaymentScheduleDto> getPaymentSchedules() {
        return paymentSchedules;
    }
}
