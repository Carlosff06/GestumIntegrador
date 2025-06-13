package com.utp.crm.config;

import com.utp.crm.service.AsistenciaChangeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebSocket
@EnableWebFlux
public class WebSocketConfig implements WebFluxConfigurer {

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public WebSocketHandler webSocketHandler(AsistenciaChangeService service) {
        return session -> service.getCambiosAsistencia()
                .flatMap(cambio -> session.send(Mono.just(session.textMessage(cambio))))
                .then();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(null), "/ws/asistencia").setAllowedOrigins("*");
    }
}

