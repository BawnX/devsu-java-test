package com.hexagonal.client.domain.ports.in;

import com.hexagonal.client.domain.models.Client;

public interface CreateClientUseCase {
    Client CreateClient(Client client);
}
