package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClientResponseDTO {

    private Long id;
    private String nombres;
    private String apellido;
    private String email;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    private List<ProductResponseDTO> productos;

    public ClientResponseDTO() {}

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public List<ProductResponseDTO> getProductos() {
        return productos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombres(String nombre) {
        this.nombres = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipoIdentificacion(String tipoDocumento) {
        this.tipoIdentificacion = tipoDocumento;
    }

    public void setNumeroIdentificacion(String numeroDocumento) {
        this.numeroIdentificacion = numeroDocumento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setProductos(List<ProductResponseDTO> productos) {
        this.productos = productos;
    }
}
