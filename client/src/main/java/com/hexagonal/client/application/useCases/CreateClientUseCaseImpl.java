package com.hexagonal.client.application.useCases;

import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.domain.ports.in.CreateClientUseCase;
import com.hexagonal.client.domain.ports.out.ClientRepositoryPort;

public class CreateClientUseCaseImpl implements CreateClientUseCase {
    private final ClientRepositoryPort clientRepositoryPort;

    public CreateClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public Client CreateClient(Client client) {
        try {
            if (clientRepositoryPort.exists(client.getClientId())) {
                throw new Exception("Client already exists");
            }

            return clientRepositoryPort.save(client);
        } catch (Exception e) {
            if (e.getMessage().equals("Client already exists")) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Cannot create client, please contact administrator");
        }
    }

}
