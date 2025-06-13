package com.utp.crm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Empleado {
    @Id
    private String id;
    @Indexed(unique = true)
    private String dni;
    private String nombre;
    private String contraseña;
    @Transient
    private Sede sede;
    @JsonIgnore
    private String sede_id;
    @Email
    @Indexed(unique = true)
    private String email;
    @NotBlank
    private String area;
    private String estado;
    private String horas_extras;
    private String rol;


    public Empleado() {
    }

    public Empleado(String id, String dni, String nombre, String contraseña, Sede sede, String sede_id, String email, String area, String estado, String horas_extras, String rol) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.sede = sede;
        this.sede_id = sede_id;
        this.email = email;
        this.area = area;
        this.estado = estado;
        this.horas_extras = horas_extras;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getSede_id() {
        return sede_id;
    }

    public void setSede_id(String sede_id) {
        this.sede_id = sede_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHoras_extras() {
        return horas_extras;
    }

    public void setHoras_extras(String horas_extras) {
        this.horas_extras = horas_extras;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
