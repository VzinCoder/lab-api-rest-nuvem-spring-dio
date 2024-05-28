package com.vzincoder.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzincoder.api.domain.Client;
import com.vzincoder.api.dto.ClientCreateDTO;
import com.vzincoder.api.dto.ClientDTO;
import com.vzincoder.api.exception.DataIntegrityException;
import com.vzincoder.api.exception.EntityNotFoundException;
import com.vzincoder.api.repository.ClientRepository;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;


     public ClientDTO getClientByCpf(String cpf) {
        Optional<Client> clientFound = clientRepository.findByCpf(cpf);

        if (clientFound.isEmpty()) {
            throw new EntityNotFoundException("client not afound");
        }

        return convertToDTO(clientFound.get());
    }

    public List<ClientDTO> getAllClient() {
        List<Client> clientList = clientRepository.findAll();

        List<ClientDTO> clientDTOList = clientList.stream()
                .map(this::convertToDTO)
                .toList();

        return clientDTOList;
    }

    public ClientDTO createClient(ClientCreateDTO client) {
        try {
            Client newClient = convertClientCreateDTOToClient(client);
            return convertToDTO(clientRepository.save(newClient));
        } catch (Exception e) {
            throw new DataIntegrityException("This Cpf is already being used!");
        }
    }

    public void deleteClient(String cpf) {
        Optional<Client> clientFound = clientRepository.findByCpf(cpf);

        if (clientFound.isEmpty()) {
            throw new EntityNotFoundException("client not afound");
        }

        clientRepository.delete(clientFound.get());
    }

    public ClientDTO updateClient(Client client) {
        Optional<Client> clientFound = clientRepository.findByCpf(client.getCpf());

        if (clientFound.isEmpty()) {
            throw new EntityNotFoundException("Client not found");
        }

        try {
            Client existingClient = clientFound.get();

            existingClient.setName(client.getName());
            existingClient.setDateOfBirth(client.getDateOfBirth());
            existingClient.setCpf(client.getCpf());

            Client updatedClient = clientRepository.save(existingClient);
            return convertToDTO(updatedClient);

        } catch (Exception e) {
            throw new DataIntegrityException("CPF is already being used by another client");
        }

    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setCpf(client.getCpf());
        clientDTO.setDateOfBirth(client.getDateOfBirth());
        clientDTO.setName(client.getName());
        return clientDTO;
    }

    private Client convertClientCreateDTOToClient(ClientCreateDTO clientCreateDTO){
        Client client = new Client();
        client.setCpf(clientCreateDTO.getCpf());
        client.setName(clientCreateDTO.getName());
        client.setDateOfBirth(LocalDate.parse(clientCreateDTO.getDateOfBirth()));
        return client;
    }

}
