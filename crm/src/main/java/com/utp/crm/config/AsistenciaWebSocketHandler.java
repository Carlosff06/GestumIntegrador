package com.utp.crm.config;

import com.utp.crm.service.AsistenciaChangeService;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class AsistenciaWebSocketHandler implements WebSocketHandler {

    private final AsistenciaChangeService asistenciaChangeService;

    public AsistenciaWebSocketHandler(AsistenciaChangeService asistenciaChangeService) {
        this.asistenciaChangeService = asistenciaChangeService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                asistenciaChangeService.getCambiosAsistencia()
                        .map(session::textMessage)
        );
    }
}

