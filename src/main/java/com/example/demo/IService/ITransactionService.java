package com.example.demo.IService;

import com.example.demo.Entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    List<Transaction> findAll(); // Listar todas

    Optional<Transaction> findById(Long id); // Buscar por id

    Transaction consignar(Long productoId, BigDecimal monto);

    Transaction retirar(Long productoId, BigDecimal monto);

    Transaction transferir(Long productoOrigenId, Long productoDestinoId, BigDecimal monto);

    List<Transaction> findByProducto(Long productoId);
}
