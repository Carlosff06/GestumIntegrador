package com.utp.crm.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class AsistenciaChangeService {

    private final Sinks.Many<String> sink = Sinks.many().replay().latest();


    public void notificarCambio(String mensaje) {
        sink.tryEmitNext(mensaje);
    }

    public Flux<String> getCambiosAsistencia() {
        return sink.asFlux().mergeWith(Flux.never());
    }
}

