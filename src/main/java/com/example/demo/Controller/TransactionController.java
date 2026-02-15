package com.example.demo.Controller;

import com.example.demo.DTO.TransactionResponseDTO;
import com.example.demo.Entity.Transaction;
import com.example.demo.IService.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transacciones")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    // LISTAR TODAS
    @GetMapping("/getTransacciones")
    public ResponseEntity<List<TransactionResponseDTO>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    // BUSCAR POR ID
    @GetMapping("/getTransaccion/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id) {

        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CONSIGNAR
    @PostMapping("/consignar")
    public ResponseEntity<TransactionResponseDTO> consignar(
            @RequestParam Long productoId,
            @RequestParam BigDecimal monto) {

        return ResponseEntity.ok(transactionService.consignar(productoId, monto));
    }

    // RETIRAR
    @PostMapping("/retirar")
    public ResponseEntity<TransactionResponseDTO> retirar(
            @RequestParam Long productoId,
            @RequestParam BigDecimal monto) {

        return ResponseEntity.ok(transactionService.retirar(productoId, monto));
    }

    // TRANSFERIR
    @PostMapping("/transferir")
    public ResponseEntity<TransactionResponseDTO> transferir(
            @RequestParam Long origenId,
            @RequestParam Long destinoId,
            @RequestParam BigDecimal monto) {

        return ResponseEntity.ok(transactionService.transferir(origenId, destinoId, monto));
    }

    // LISTAR TRANSACCIONES POR PRODUCTO
    @GetMapping("/getTransaccionesByProducto/{productoId}")
    public ResponseEntity<List<TransactionResponseDTO>> findByProducto(@PathVariable Long productoId) {

        return ResponseEntity.ok(transactionService.findByProducto(productoId));
    }
}
