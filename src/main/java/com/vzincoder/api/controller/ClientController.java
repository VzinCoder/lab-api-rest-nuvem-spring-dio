package com.vzincoder.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.vzincoder.api.domain.Client;
import com.vzincoder.api.dto.ClientCreateDTO;
import com.vzincoder.api.dto.ClientDTO;
import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.service.ClientService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable String cpf) {
        return ResponseEntity.ok(clientService.getClientByCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClient();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        ClientDTO createdClient = clientService.createClient(clientCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable String cpf,
            @Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        Client updateClient = new Client();
        updateClient.setCpf(cpf);
        updateClient.setName(clientCreateDTO.getName());
        updateClient.setDateOfBirth(LocalDate.parse(clientCreateDTO.getDateOfBirth()));
        ClientDTO updatedClient = clientService.updateClient(updateClient);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<MessageResponseDTO> deleteClient(@PathVariable String cpf) {
        clientService.deleteClient(cpf);
        return ResponseEntity.ok(new MessageResponseDTO("Client with CPF " + cpf + " deleted successfully"));
    }

}
