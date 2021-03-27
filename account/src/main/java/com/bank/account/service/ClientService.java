package com.bank.account.service;

import com.bank.account.entity.Client;
import com.bank.account.enumerator.ClientTypeEnum;
import com.bank.account.enumerator.ResponseMessageEnum;
import com.bank.account.repository.ClientRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ResponseEntity<String> addClient(Client client) {
        ResponseMessageEnum responseMessage = validateFields(client);
        if (!responseMessage.equals(ResponseMessageEnum.M100)) {
            return new ResponseEntity<>(environment.getProperty(responseMessage.getDescription()), HttpStatus.BAD_REQUEST);
        }
        Client existingClient = clientRepository.findClientByDocumentNumber(client.getDocumentNumber());
        if (existingClient != null) {
            return new ResponseEntity<>(environment.getProperty("message.docAlreadyRegistered"), HttpStatus.BAD_REQUEST);
        }
        clientRepository.save(client);
        rabbitTemplate.convertAndSend("spring-boot-exchange", "account", client.getId());
        return new ResponseEntity<>(environment.getProperty("message.clientCreated"), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Client> getClientById(String id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseMessageEnum validateFields(Client client) {
        if (client.getClientType().equals(ClientTypeEnum.PF) && !(client.getDocumentNumber().matches("[0-9]+") && client.getDocumentNumber().length() == 11)) {
            return ResponseMessageEnum.M203;
        } else if (client.getClientType().equals(ClientTypeEnum.PJ) && !(client.getDocumentNumber().matches("[0-9]+") && client.getDocumentNumber().length() == 14)) {
            return ResponseMessageEnum.M203;
        }
        if (client.getScore() < 0 || client.getScore() > 9) {
            return ResponseMessageEnum.M202;
        }
        return ResponseMessageEnum.M100;
    }

    public Optional<Client> findById(String id){
        return clientRepository.findById(id);
    }
}