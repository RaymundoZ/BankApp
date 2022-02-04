package com.raymundo.bankapp.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "PAYMENT_SCHEDULE")
@Table(name = "PAYMENT_SCHEDULE")
public class PaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Column(name = "loan_repayment_amount")
    private double loanRepaymentAmount;

    @Column(name = "interest_payment_amount")
    private double interestPaymentAmount;

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
