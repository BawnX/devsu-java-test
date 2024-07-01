package com.hexagonal.client.application.services;

import java.util.List;
import java.util.Optional;

import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.domain.ports.in.CreateClientUseCase;
import com.hexagonal.client.domain.ports.in.DeleteClientUseCase;
import com.hexagonal.client.domain.ports.in.RetrieveClientUseCase;
import com.hexagonal.client.domain.ports.in.UpdateClientUseCase;

public class ClientService implements
        RetrieveClientUseCase,
        CreateClientUseCase,
        UpdateClientUseCase,
        DeleteClientUseCase {
    private final RetrieveClientUseCase retrieveClientUseCase;
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;

    public ClientService(RetrieveClientUseCase retrieveClientUseCase, CreateClientUseCase createClientUseCase,
            UpdateClientUseCase updateClientUseCase, DeleteClientUseCase deleteClientUseCase) {
        this.retrieveClientUseCase = retrieveClientUseCase;
        this.createClientUseCase = createClientUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.deleteClientUseCase = deleteClientUseCase;
    }

    @Override
    public Optional<Client> getClient(String id) {
        return retrieveClientUseCase.getClient(id);
    }

    @Override
    public List<Client> getAllClients() {
        return retrieveClientUseCase.getAllClients();
    }

    @Override
    public Client CreateClient(Client client) {
        return createClientUseCase.CreateClient(client);
    }

    @Override
    public Optional<Client> updateClient(String id, Client updateClient) {
        return updateClientUseCase.updateClient(id, updateClient);
    }

    @Override
    public Optional<Client> partialUpdateClient(String id, Optional<Client> partialClient) {
        return updateClientUseCase.partialUpdateClient(id, partialClient);
    }

    @Override
    public boolean DeleteClient(String id) {
        return deleteClientUseCase.DeleteClient(id);
    }
}
