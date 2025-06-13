package com.utp.crm.repository;

import com.utp.crm.model.Empleado;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface EmpleadoRepository extends ReactiveMongoRepository<Empleado, String> {
    Mono<Empleado> findByDni(String dni);

     Mono<Empleado> findByEmail(String username);


    Flux<Empleado> findEmpleadoByRol(String usuario);
}
