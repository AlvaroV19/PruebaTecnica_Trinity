package com.example.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoCuenta; // AHORRO o CORRIENTE
    private String numeroCuenta;
    private BigDecimal saldo;
    private String estado; // ACTIVA o CANCELADA

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Client client;

    public Product() {}

    public Long getId() {
        return id;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getEstado() {
        return estado;
    }

    public Client getClient() {
        return client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
