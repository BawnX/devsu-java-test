package com.hexagonal.client.domain.ports.in;

import java.util.List;
import java.util.Optional;

import com.hexagonal.client.domain.models.Client;

public interface RetrieveClientUseCase {
    Optional<Client> getClient(String id);

    List<Client> getAllClients();
}
