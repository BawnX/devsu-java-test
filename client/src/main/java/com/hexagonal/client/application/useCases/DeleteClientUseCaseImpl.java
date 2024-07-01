package com.hexagonal.client.application.useCases;

import com.hexagonal.client.domain.ports.in.DeleteClientUseCase;
import com.hexagonal.client.domain.ports.out.ClientRepositoryPort;

public class DeleteClientUseCaseImpl implements DeleteClientUseCase {
    private final ClientRepositoryPort clientRepositoryPort;

    public DeleteClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public boolean DeleteClient(String id) {
        try {
            if (!clientRepositoryPort.exists(id)) {
                return false;
            }

            return clientRepositoryPort.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete client, please contact administrator");
        }
    }

}
