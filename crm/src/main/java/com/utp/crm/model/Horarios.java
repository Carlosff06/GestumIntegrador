package com.utp.crm.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "horarios")
public class Horarios {

    @Id
    private String id;
    private ObjectId empleadoId;
    private LocalDate fechaInicio;  // 2025-06-09
    private LocalDate fechaFin;
    private List<Dia> dias;
    private Integer totalSemanal;

    public Horarios() {
    }

    public Horarios(String id, ObjectId empleadoId, LocalDate fechaInicio, LocalDate fechaFin, List<Dia> dias, Integer totalSemanal) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.dias = dias;
        this.totalSemanal = totalSemanal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectId getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(ObjectId empleadoId) {
        this.empleadoId = empleadoId;
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

    public List<Dia> getDias() {
        return dias;
    }

    public void setDias(List<Dia> dias) {
        this.dias = dias;
    }

    public Integer getTotalSemanal() {
        return totalSemanal;
    }

    public void setTotalSemanal(Integer totalSemanal) {
        this.totalSemanal = totalSemanal;
    }
}
