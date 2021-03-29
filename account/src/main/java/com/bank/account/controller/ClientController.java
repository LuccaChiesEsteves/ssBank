package com.bank.account.controller;

import com.bank.account.entity.Client;
import com.bank.account.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(value="Clients")
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value="Add a client")
    @PostMapping
    public ResponseEntity<String> add(@Valid  @RequestBody Client client) {
        return clientService.addClient(client);
    }

    @ApiOperation(value = "Return a list containing all clients")
    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return clientService.getClients();
    }

    @ApiOperation(value = "Return a specific client")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> getClient(@NotEmpty @PathVariable String id) {
        return clientService.getClientById(id);
    }
}
