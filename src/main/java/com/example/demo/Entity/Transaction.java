package com.example.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // CONSIGNACION, RETIRO, TRANSFERENCIA
    private BigDecimal monto;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Product product;

    public Transaction() {}

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
