package com.hexagonal.client.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexagonal.client.application.services.ClientService;
import com.hexagonal.client.application.useCases.CreateClientUseCaseImpl;
import com.hexagonal.client.application.useCases.DeleteClientUseCaseImpl;
import com.hexagonal.client.application.useCases.RetrieveClientUseCaseImpl;
import com.hexagonal.client.application.useCases.UpdateClientUseCaseImpl;
import com.hexagonal.client.domain.ports.out.ClientRepositoryPort;
import com.hexagonal.client.infrastructure.repositories.ClientRepository;

@Configuration
public class ApplicationConfig {
    @Bean
    public ClientService clientService(ClientRepositoryPort clientRepositoryPort) {
        return new ClientService(
                new RetrieveClientUseCaseImpl(clientRepositoryPort),
                new CreateClientUseCaseImpl(clientRepositoryPort),
                new UpdateClientUseCaseImpl(clientRepositoryPort),
                new DeleteClientUseCaseImpl(clientRepositoryPort));
    }

    @Bean
    public ClientRepositoryPort clientRepositoryPort(ClientRepository clientRepository) {
        return clientRepository;
    }
}
