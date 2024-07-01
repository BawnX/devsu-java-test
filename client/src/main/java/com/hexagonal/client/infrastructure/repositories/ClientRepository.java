package com.hexagonal.client.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.domain.ports.out.ClientRepositoryPort;
import com.hexagonal.client.infrastructure.adapters.ClientAdapter;
import com.hexagonal.client.infrastructure.entities.ClientEntity;
import com.hexagonal.client.infrastructure.jpa.JpaClientRepository;

@Component
public class ClientRepository implements ClientRepositoryPort {
    private final JpaClientRepository jpaRepository;
    private final ClientAdapter adapter = new ClientAdapter();

    public ClientRepository(JpaClientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = adapter.toEntity(client);

        return adapter.toModel(jpaRepository.save(entity));
    }

    @Override
    public Optional<Client> findById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return jpaRepository.findById(uuid).map(adapter::toModel);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream()
                .map(adapter::toModel)
                .toList();
    }

    @Override
    public Optional<Client> update(Client updateClient) {
        ClientEntity entity = adapter.toEntity(updateClient);
        ClientEntity updatedEntity = jpaRepository.save(entity);
        return Optional.of(adapter.toModel(updatedEntity));
    }

    @Override
    public Optional<Client> partialUpdate(Optional<Client> partialClient) {
        if (partialClient.isPresent()) {
            ClientEntity updatedEntity = adapter.toEntity(partialClient.get());
            ClientEntity savedEntity = jpaRepository.save(updatedEntity);
            return Optional.of(adapter.toModel(savedEntity));
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            jpaRepository.deleteById(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean exists(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return jpaRepository.existsById(uuid);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
