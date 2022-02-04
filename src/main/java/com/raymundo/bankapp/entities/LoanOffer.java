package com.raymundo.bankapp.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "LOAN_OFFER")
@Table(name = "LOAN_OFFER")
public class LoanOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "client_uuid",
            referencedColumnName = "uuid")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_uuid",
            referencedColumnName = "uuid")
    private Credit credit;

    @Column(name = "credit_amount")
    private double creditAmount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "LOAN_PAYMENT_SCHEDULE",
            joinColumns = @JoinColumn(name = "loan_offer_uuid"),
            inverseJoinColumns = @JoinColumn(name = "payment_schedule_uuid"))
    private List<PaymentSchedule> paymentSchedules;

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public void setPaymentSchedules(List<PaymentSchedule> paymentSchedules) {
        this.paymentSchedules = paymentSchedules;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Client getClient() {
        return client;
    }

    public Credit getCredit() {
        return credit;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public List<PaymentSchedule> getPaymentSchedules() {
        return paymentSchedules;
    }
}
