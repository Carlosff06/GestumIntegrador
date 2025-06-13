package com.utp.crm.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "solicitudes_vacaciones")
public class SolicitudVacaciones {

    @Id
    private String id;
    @NotBlank
    private String nombres;
    @NotBlank
    private String apellidos;
    @NotBlank
    private String dni;
    @NotBlank
    private String area;
    @Email
    private String correo;
    @NotBlank
    private String codigoEmpleado;
    @NotBlank
    private String jefeDirecto;
    @NotBlank
    private String codigoEncargado;
    @NotBlank
    private LocalDate fechaInicio;
    @NotBlank
    private LocalDate fechaFin;
    private String estado;
    private LocalDate fechaSolicitud;

    public SolicitudVacaciones(String id, String nombres, String apellidos, String dni, String area, String correo, String codigoEmpleado, String jefeDirecto, String codigoEncargado, LocalDate fechaInicio, LocalDate fechaFin, String estado, LocalDate fechaSolicitud) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.area = area;
        this.correo = correo;
        this.codigoEmpleado = codigoEmpleado;
        this.jefeDirecto = jefeDirecto;
        this.codigoEncargado = codigoEncargado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getJefeDirecto() {
        return jefeDirecto;
    }

    public void setJefeDirecto(String jefeDirecto) {
        this.jefeDirecto = jefeDirecto;
    }

    public String getCodigoEncargado() {
        return codigoEncargado;
    }

    public void setCodigoEncargado(String codigoEncargado) {
        this.codigoEncargado = codigoEncargado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
