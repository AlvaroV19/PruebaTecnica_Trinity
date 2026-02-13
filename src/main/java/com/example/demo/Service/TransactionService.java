package com.example.demo.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.Transaction;
import com.example.demo.IService.ITransactionService;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction consignar(Long productoId, BigDecimal monto) {

        Product product = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setSaldo(product.getSaldo().add(monto));
        productRepository.save(product);

        Transaction tx = new Transaction();
        tx.setProduct(product);
        tx.setMonto(monto);
        tx.setTipo("CONSIGNACION");
        tx.setFecha(LocalDateTime.now());

        return transactionRepository.save(tx);
    }

    @Override
    public Transaction retirar(Long productoId, BigDecimal monto) {

        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getSaldo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        producto.setSaldo(producto.getSaldo().subtract(monto));
        productRepository.save(producto);

        Transaction tx = new Transaction();
        tx.setProduct(producto);
        tx.setMonto(monto);
        tx.setTipo("RETIRO");
        tx.setFecha(LocalDateTime.now());

        return transactionRepository.save(tx);
    }

    @Override
    public Transaction transferir(Long productoOrigenId, Long productoDestinoId, BigDecimal monto) {

        Product origen = productRepository.findById(productoOrigenId)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));

        Product destino = productRepository.findById(productoDestinoId)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

        if (origen.getSaldo().compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        productRepository.save(origen);
        productRepository.save(destino);

        Transaction tx = new Transaction();
        tx.setProduct(origen);
        tx.setMonto(monto);
        tx.setTipo("TRANSFERENCIA");
        tx.setFecha(LocalDateTime.now());

        return transactionRepository.save(tx);
    }

    @Override
    public List<Transaction> findByProducto(Long productId) {
        return transactionRepository.findByProduct_Id(productId);
    }
}
