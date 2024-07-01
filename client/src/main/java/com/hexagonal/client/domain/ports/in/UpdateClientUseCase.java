package com.hexagonal.client.domain.ports.in;

import java.util.Optional;

import com.hexagonal.client.domain.models.Client;

public interface UpdateClientUseCase {
    Optional<Client> updateClient(String id, Client updateClient);

    Optional<Client> partialUpdateClient(String id, Optional<Client> partialClient);
}
