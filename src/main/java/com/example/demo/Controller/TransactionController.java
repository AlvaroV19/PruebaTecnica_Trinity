package com.example.demo.Controller;

import com.example.demo.Entity.Transaction;
import com.example.demo.IService.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/transacciones")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    // LISTAR TODAS
    @GetMapping("/getTransacciones")
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    // BUSCAR POR ID
    @GetMapping("/getTransaccion/{id}")
    public Optional<Transaction> findById(@PathVariable Long id) {
        return transactionService.findById(id);
    }

    // CONSIGNAR
    @PostMapping("/consignar")
    public Transaction consignar(@RequestParam Long productoId,
                                 @RequestParam BigDecimal monto) {
        return transactionService.consignar(productoId, monto);
    }

    // RETIRAR
    @PostMapping("/retirar")
    public Transaction retirar(@RequestParam Long productoId,
                               @RequestParam BigDecimal monto) {
        return transactionService.retirar(productoId, monto);
    }

    // TRANSFERIR
    @PostMapping("/transferir")
    public Transaction transferir(@RequestParam Long origenId,
                                  @RequestParam Long destinoId,
                                  @RequestParam BigDecimal monto) {
        return transactionService.transferir(origenId, destinoId, monto);
    }

    // LISTAR TRANSACCIONES POR PRODUCTO
    @GetMapping("/getTransaccionesByProducto/{productoId}")
    public List<Transaction> findByProducto(@PathVariable Long productoId) {
        return transactionService.findByProducto(productoId);
    }
}
