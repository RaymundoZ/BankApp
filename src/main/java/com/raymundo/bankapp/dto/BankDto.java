package com.raymundo.bankapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class BankDto extends BaseDto {

    private UUID uuid;

    @NotNull
    @NotEmpty
    private List<ClientDto> clientDtos;

    @NotNull
    @NotEmpty
    private List<CreditDto> creditDtos;

    public BankDto(List<ClientDto> clientDtos, List<CreditDto> creditDtos) {
        this.uuid = UUID.randomUUID();
        this.clientDtos = clientDtos;
        this.creditDtos = creditDtos;
    }

    public BankDto(UUID uuid, List<ClientDto> clientDtos, List<CreditDto> creditDtos) {
        this.uuid = uuid;
        this.clientDtos = clientDtos;
        this.creditDtos = creditDtos;
    }

    public BankDto() {

    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setClients(List<ClientDto> clientDtos) {
        this.clientDtos = clientDtos;
    }

    public void setCredits(List<CreditDto> creditDtos) {
        this.creditDtos = creditDtos;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<ClientDto> getClients() {
        return clientDtos;
    }

    public List<CreditDto> getCredits() {
        return creditDtos;
    }
}
