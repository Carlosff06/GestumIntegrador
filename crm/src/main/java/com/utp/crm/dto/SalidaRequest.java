package com.utp.crm.dto;

import java.time.LocalDateTime;

public record SalidaRequest(LocalDateTime horaSalida, String empleadoId) {
}
