package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientRequestDTO {

    private String nombres;
    private String apellido;
    private String email;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private LocalDate fechaNacimiento;

    public ClientRequestDTO() {}

    public String getNombres() {
        return nombres;
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

    public String getEmail() {
        return email;
    }

    public String getApellido() {
        return apellido;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
