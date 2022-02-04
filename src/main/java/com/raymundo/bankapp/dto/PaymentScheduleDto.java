package com.raymundo.bankapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class PaymentScheduleDto extends BaseDto {

    private UUID uuid;

    @NotNull
    private Date paymentDate;

    @NotNull
    @Min(1)
    private double paymentAmount;

    @NotNull
    @Min(1)
    private double loanRepaymentAmount;

    @NotNull
    @Min(1)
    private double interestPaymentAmount;

    public PaymentScheduleDto(int year, int month, int day, double paymentAmount, double loanRepaymentAmount, double interestPaymentAmount) {
        this.uuid = UUID.randomUUID();
        Calendar calendar = new GregorianCalendar(year, month, day);
        paymentDate = calendar.getTime();
        this.paymentAmount = paymentAmount;
        this.loanRepaymentAmount = loanRepaymentAmount;
        this.interestPaymentAmount = interestPaymentAmount;
    }

    public PaymentScheduleDto(UUID uuid, int year, int month, int day, double paymentAmount, double loanRepaymentAmount, double interestPaymentAmount) {
        this.uuid = uuid;
        Calendar calendar = new GregorianCalendar(year, month, day);
        paymentDate = calendar.getTime();
        this.paymentAmount = paymentAmount;
        this.loanRepaymentAmount = loanRepaymentAmount;
        this.interestPaymentAmount = interestPaymentAmount;
    }

    public PaymentScheduleDto(Date paymentDate, double paymentAmount, double loanRepaymentAmount, double interestPaymentAmount) {
        this.uuid = UUID.randomUUID();
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.loanRepaymentAmount = loanRepaymentAmount;
        this.interestPaymentAmount = interestPaymentAmount;
    }

    public PaymentScheduleDto(UUID uuid, Date paymentDate, double paymentAmount, double loanRepaymentAmount, double interestPaymentAmount) {
        this.uuid = uuid;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.loanRepaymentAmount = loanRepaymentAmount;
        this.interestPaymentAmount = interestPaymentAmount;
    }

    public PaymentScheduleDto() {

    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setLoanRepaymentAmount(double loanRepaymentAmount) {
        this.loanRepaymentAmount = loanRepaymentAmount;
    }

    public void setInterestPaymentAmount(double interestPaymentAmount) {
        this.interestPaymentAmount = interestPaymentAmount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public double getLoanRepaymentAmount() {
        return loanRepaymentAmount;
    }

    public double getInterestPaymentAmount() {
        return interestPaymentAmount;
    }
}
