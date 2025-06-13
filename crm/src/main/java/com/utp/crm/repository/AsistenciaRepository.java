package com.utp.crm.repository;

import com.utp.crm.model.AsistenciaMensual;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AsistenciaRepository extends ReactiveMongoRepository<AsistenciaMensual, String> {
    Mono<AsistenciaMensual> findByEmpleadoIdAndAnioAndMes(String personaId, int anio, int mes);



}
