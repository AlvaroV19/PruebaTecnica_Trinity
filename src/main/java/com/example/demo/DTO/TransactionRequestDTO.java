package com.example.demo.DTO;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    private String tipo; // DEPOSITO / RETIRO
    private BigDecimal monto;
    private Long productId;

    public TransactionRequestDTO() {}

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}
