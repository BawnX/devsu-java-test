package com.hexagonal.client.infrastructure.adapters;

import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.infrastructure.dtos.ClienteDto;
import com.hexagonal.client.infrastructure.entities.ClientEntity;
import com.hexagonal.client.infrastructure.entities.PersonEntity;

public class ClientAdapter {
    public Client toModel(ClientEntity clientEntity) {
        PersonEntity personEntity = clientEntity.getPerson();
        return Client.CreateFromEntity(
                clientEntity.getId().toString(),
                clientEntity.getSecretPassword(),
                clientEntity.getSecretKey(),
                clientEntity.getState(),
                personEntity.getId().toString(),
                personEntity.getName(),
                personEntity.getGender(),
                personEntity.getAge(),
                personEntity.getAddress(),
                personEntity.getCellphone());
    }

    public ClientEntity toEntity(Client clientModel) {
        PersonEntity personEntity = new PersonEntity(
                clientModel.getAddress(),
                clientModel.getAge(),
                clientModel.getCellphone(),
                clientModel.getGender(),
                clientModel.getPersonUUID(),
                clientModel.getName());
        return new ClientEntity(
                clientModel.getClientUUID(),
                clientModel.getSecretPassword(),
                clientModel.getSecretKey(),
                personEntity,
                clientModel.getState());
    }

    public ClienteDto toDto(Client clientModel) {
        return new ClienteDto(
                clientModel.getClientId(),
                clientModel.getId(),
                clientModel.getPassword(),
                clientModel.getAddress(),
                clientModel.getAge().toString(),
                clientModel.getState(),
                clientModel.getGender(),
                clientModel.getName(),
                clientModel.getCellphone());
    }

    public Client fromDto(ClienteDto clienteDto) {
        Integer age = 0;
        if (clienteDto.getEdad() != null) {
            age = Integer.valueOf(clienteDto.getEdad());
        }
        return Client.CreateFromRequest(
                clienteDto.getClienteId(),
                clienteDto.getContraseña(),
                clienteDto.getEstado(),
                clienteDto.getPersonaId(),
                clienteDto.getNombre(),
                clienteDto.getGenero(),
                age,
                clienteDto.getDireccion(),
                clienteDto.getTelefono());
    }
}
