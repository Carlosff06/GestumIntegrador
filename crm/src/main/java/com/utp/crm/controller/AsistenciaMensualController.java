package com.utp.crm.controller;

import com.utp.crm.dto.EntradaRequest;
import com.utp.crm.dto.SalidaRequest;
import com.utp.crm.model.AsistenciaMensual;
import com.utp.crm.service.AsistenciaChangeService;
import com.utp.crm.service.AsistenciaMensualService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaMensualController {

    @Autowired
    private AsistenciaMensualService asistenciaMensualService;

    private final AsistenciaChangeService asistenciaChangeService;

    public AsistenciaMensualController(AsistenciaChangeService asistenciaChangeService) {
        this.asistenciaChangeService = asistenciaChangeService;
    }

    @GetMapping("/registrar-dias")
    public Mono<AsistenciaMensual> registrarDias(@RequestParam String empleadoId){
        if (StringUtils.isBlank(empleadoId)) {
            return Mono.error(new IllegalArgumentException("El ID del empleado no puede estar vacío"));
        }
        return asistenciaMensualService.registrarAsistenciaDelMes(empleadoId);
    }

    @PostMapping("/marcar-entrada")
    public Mono<EntradaRequest> marcarEntrada(@RequestBody EntradaRequest entradaRequest){
        if (StringUtils.isBlank(entradaRequest.empleadoId())) {
            return Mono.error(new IllegalArgumentException("El ID del empleado no puede estar vacío"));
        }
        return asistenciaMensualService.registrarEntrada(entradaRequest)
                .doOnSuccess(x -> {
                    System.out.println("✅ Enviando cambio por salida: " + entradaRequest.empleadoId());
                    asistenciaChangeService.notificarCambio("salida:" + entradaRequest.empleadoId());
                }).thenReturn(entradaRequest);
    }
    @PostMapping("/marcar-salida")
    public Mono<SalidaRequest> marcarSalida(@RequestBody SalidaRequest salidaRequest){

        return asistenciaMensualService.registrarSalida(salidaRequest)
                .doOnSuccess(x -> asistenciaChangeService.notificarCambio("salida:" + salidaRequest.empleadoId()))
                .thenReturn(salidaRequest); }

}
