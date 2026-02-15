package com.example.demo.Service;

import com.example.demo.DTO.TransactionRequestDTO;
import com.example.demo.DTO.TransactionResponseDTO;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Transaction;
import com.example.demo.IService.ITransactionService;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    // CONVERTIDOR MANUAL (sin mapper)
    private TransactionResponseDTO toDTO(Transaction tx) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(tx.getId());
        dto.setTipo(tx.getTipo());
        dto.setMonto(tx.getMonto());
        dto.setFecha(tx.getFecha());
        dto.setProductId(tx.getProduct().getId());
        return dto;
    }

    @Override
    public List<TransactionResponseDTO> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransactionResponseDTO> findById(Long id) {
        return transactionRepository.findById(id)
                .map(this::toDTO);
    }

    @Transactional
    @Override
    public TransactionResponseDTO consignar(Long productoId, BigDecimal monto) {

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a cero");
        }

        Product product = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setSaldo(product.getSaldo().add(monto));
        productRepository.save(product);

        Transaction tx = new Transaction();
        tx.setTipo("CONSIGNACION");
        tx.setMonto(monto);
        tx.setFecha(LocalDateTime.now());
        tx.setProduct(product);

        transactionRepository.save(tx);

        return toDTO(tx);
    }

    @Transactional
    @Override
    public TransactionResponseDTO retirar(Long productoId, BigDecimal monto) {

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a cero");
        }

        Product product = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getSaldo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        product.setSaldo(product.getSaldo().subtract(monto));
        productRepository.save(product);

        Transaction tx = new Transaction();
        tx.setTipo("RETIRO");
        tx.setMonto(monto);
        tx.setFecha(LocalDateTime.now());
        tx.setProduct(product);

        transactionRepository.save(tx);

        return toDTO(tx);
    }

    @Transactional
    @Override
    public TransactionResponseDTO transferir(Long origenId, Long destinoId, BigDecimal monto) {

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Monto inválido");
        }

        Product origen = productRepository.findById(origenId)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));

        Product destino = productRepository.findById(destinoId)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

        if (!"ACTIVA".equalsIgnoreCase(origen.getEstado()) ||
                !"ACTIVA".equalsIgnoreCase(destino.getEstado())) {
            throw new RuntimeException("Ambas cuentas deben estar activas");
        }

        validarSaldo(origen, monto);

        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        productRepository.save(origen);
        productRepository.save(destino);

        Transaction debito = new Transaction();
        debito.setTipo("TRANSFERENCIA_DEBITO");
        debito.setMonto(monto);
        debito.setFecha(LocalDateTime.now());
        debito.setProduct(origen);

        Transaction credito = new Transaction();
        credito.setTipo("TRANSFERENCIA_CREDITO");
        credito.setMonto(monto);
        credito.setFecha(LocalDateTime.now());
        credito.setProduct(destino);

        transactionRepository.save(debito);
        transactionRepository.save(credito);

        return toDTO(debito);
    }

    @Override
    public List<TransactionResponseDTO> findByProducto(Long productoId) {

        return transactionRepository.findByProduct_Id(productoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private void validarSaldo(Product product, BigDecimal monto) {
        if (product.getSaldo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

}
