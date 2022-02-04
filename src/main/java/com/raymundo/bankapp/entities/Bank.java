package com.raymundo.bankapp.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "BANK")
@Table(name = "BANK")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @OneToMany
    @JoinTable(name = "BANK_CLIENT",
            joinColumns = @JoinColumn(name = "bank_uuid"),
            inverseJoinColumns = @JoinColumn(name = "client_uuid"))
    private List<Client> clients;

    @OneToMany
    @JoinTable(name = "BANK_CREDIT",
            joinColumns = @JoinColumn(name = "bank_uuid"),
            inverseJoinColumns = @JoinColumn(name = "credit_uuid"))
    private List<Credit> credits;


    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Credit> getCredits() {
        return credits;
    }
}
