package com.example.demo.Controller;

import com.example.demo.DTO.ClientRequestDTO;
import com.example.demo.DTO.ClientResponseDTO;
import com.example.demo.Entity.Client;
import com.example.demo.IService.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private IClientService clientService;

    // LISTAR TODOS
    @GetMapping("/getClientes")
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    // BUSCAR POR ID
    @GetMapping("/getCliente/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    // CREAR CLIENTE
    @PostMapping("/saveCliente")
    public ResponseEntity<ClientResponseDTO> save(@RequestBody ClientRequestDTO dto) {
        ClientResponseDTO response = clientService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/updateCliente/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody ClientRequestDTO dto) {

        return ResponseEntity.ok(clientService.update(id, dto));
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/deleteCliente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
