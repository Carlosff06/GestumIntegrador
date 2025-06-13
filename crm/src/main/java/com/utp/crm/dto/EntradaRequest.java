package com.utp.crm.dto;

import java.time.LocalDateTime;

public record EntradaRequest(LocalDateTime horaEntrada, String empleadoId) {
}
