package com.example.demo.Controller;

import com.example.demo.Entity.Client;
import com.example.demo.IService.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private IClientService clientService;

    // LISTAR TODOS
    @GetMapping("/getClientes")
    public List<Client> findAll() {
        return clientService.findAll();
    }

    // BUSCAR POR ID
    @GetMapping("/getCliente/{id}")
    public Optional<Client> findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    // CREAR CLIENTE
    @PostMapping("/saveCliente")
    public Client save(@RequestBody Client client) {
        return clientService.save(client);
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/updateCliente/{id}")
    public void update(@RequestBody Client client, @PathVariable Long id) {
        clientService.update(client, id);
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/deleteCliente/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
