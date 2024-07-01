package com.hexagonal.client.application.useCases;

import java.util.List;
import java.util.Optional;

import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.domain.ports.in.RetrieveClientUseCase;
import com.hexagonal.client.domain.ports.out.ClientRepositoryPort;

public class RetrieveClientUseCaseImpl implements RetrieveClientUseCase {
    private final ClientRepositoryPort clientRepositoryPort;

    public RetrieveClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public Optional<Client> getClient(String id) {
        return clientRepositoryPort.findById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepositoryPort.findAll();
    }

}
