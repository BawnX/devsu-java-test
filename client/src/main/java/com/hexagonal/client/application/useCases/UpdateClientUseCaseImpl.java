package com.hexagonal.client.application.useCases;

import java.util.Optional;

import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.domain.models.valueObjects.Guid;
import com.hexagonal.client.domain.ports.in.UpdateClientUseCase;
import com.hexagonal.client.domain.ports.out.ClientRepositoryPort;

public class UpdateClientUseCaseImpl implements UpdateClientUseCase {
    private final ClientRepositoryPort clientRepositoryPort;

    public UpdateClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public Optional<Client> updateClient(String id, Client updateClient) {
        try {
            if (id == null || id.isEmpty()) {
                throw new Exception("Id is required");
            }

            if (!clientRepositoryPort.exists(id)) {
                return Optional.empty();
            }

            updateClient.setClientId(new Guid(id));

            return clientRepositoryPort.update(updateClient);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create client, please contact administrator");
        }
    }

    @Override
    public Optional<Client> partialUpdateClient(String id, Optional<Client> partialClient) {
        try {
            if (id == null || id.isEmpty()) {
                throw new Exception("Id is required");
            }

            Optional<Client> client = clientRepositoryPort.findById(id);

            if (!client.isPresent()) {
                return Optional.empty();
            }

            Client updatedClient = partialClient
                    .map((Client pc) -> client.get().merge(pc))
                    .orElseGet(client::get);

            return clientRepositoryPort.partialUpdate(Optional.of(updatedClient));
        } catch (Exception e) {
            throw new RuntimeException("Cannot create client, please contact administrator, ", e);
        }
    }

}
