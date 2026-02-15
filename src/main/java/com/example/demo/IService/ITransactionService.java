package com.example.demo.IService;

import com.example.demo.DTO.TransactionRequestDTO;
import com.example.demo.DTO.TransactionResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    List<TransactionResponseDTO> findAll();

    Optional<TransactionResponseDTO> findById(Long id);

    TransactionResponseDTO consignar(Long productoId, BigDecimal monto);

    TransactionResponseDTO retirar(Long productoId, BigDecimal monto);

    TransactionResponseDTO transferir(Long origenId, Long destinoId, BigDecimal monto);

    List<TransactionResponseDTO> findByProducto(Long productoId);
}
