package com.hexagonal.client.infrastructure.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonal.client.application.services.ClientService;
import com.hexagonal.client.domain.models.Client;
import com.hexagonal.client.infrastructure.adapters.ClientAdapter;
import com.hexagonal.client.infrastructure.dtos.ClienteDto;

@RestController
@RequestMapping("clientes")
public class ClientController {
    private final ClientService clientService;
    private final ClientAdapter adapter = new ClientAdapter();

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> getAllClient() {
        List<ClienteDto> Clients = clientService.getAllClients().stream().map(adapter::toDto).toList();
        return new ResponseEntity<>(Clients, HttpStatus.OK);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDto> getClientById(@PathVariable String clienteId) {
        return clientService.getClient(clienteId)
                .map(Client -> new ResponseEntity<ClienteDto>(adapter.toDto(Client), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClienteDto> createClient(@RequestBody ClienteDto client) {
        Client createdClient = clientService.CreateClient(adapter.fromDto(client));
        return new ResponseEntity<>(adapter.toDto(createdClient), HttpStatus.CREATED);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteDto> updateClient(@PathVariable String clienteId, @RequestBody ClienteDto client) {
        return clientService.updateClient(clienteId, adapter.fromDto(client))
                .map(updatedClient -> new ResponseEntity<>(adapter.toDto(updatedClient), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{clienteId}")
    public ResponseEntity<ClienteDto> updatePartialClient(@PathVariable String clienteId,
            @RequestBody Optional<ClienteDto> client) {
        return clientService.partialUpdateClient(clienteId, Optional.of(adapter.fromDto(client.get())))
                .map(updatedClient -> new ResponseEntity<>(adapter.toDto(updatedClient), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable String clienteId) {
        if (clientService.DeleteClient(clienteId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
