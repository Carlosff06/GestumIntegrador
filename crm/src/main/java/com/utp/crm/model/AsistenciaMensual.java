package com.utp.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "asistencias")
public class AsistenciaMensual {
    @Id
    private String id;

    private String empleadoId;
    private int anio;
    private int mes;

    private List<DiaAsistencia> asistencias;

    public AsistenciaMensual() {
    }

    public AsistenciaMensual(String id, String personaId,  int anio, int mes, List<DiaAsistencia> asistencias) {
        this.id = id;
        this.empleadoId = personaId;
        this.anio = anio;
        this.mes = mes;
        this.asistencias = asistencias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonaId() {
        return empleadoId;
    }

    public void setPersonaId(String personaId) {
        this.empleadoId = personaId;
    }



    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public List<DiaAsistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<DiaAsistencia> asistencias) {
        this.asistencias = asistencias;
    }
}

