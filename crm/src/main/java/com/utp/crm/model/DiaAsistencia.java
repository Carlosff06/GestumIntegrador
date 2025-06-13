package com.utp.crm.model;

import java.time.LocalDateTime;

public class DiaAsistencia {
    private int dia;
    private String estado; // "asistio", "no_asistio", "pendiente"
    private LocalDateTime horaLlegada;
    private LocalDateTime horaSalida;
    private String tiempoTotal;

    public DiaAsistencia() {
    }

    public DiaAsistencia(int dia, String estado, LocalDateTime horaLlegada, LocalDateTime horaSalida, String tiempoTotal) {
        this.dia = dia;
        this.estado = estado;
        this.horaLlegada = horaLlegada;
        this.horaSalida = horaSalida;
        this.tiempoTotal = tiempoTotal;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
}

