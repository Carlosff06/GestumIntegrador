package com.utp.crm.controller;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.utp.crm.dto.EmpleadoConAsistencia;
import com.utp.crm.model.AsistenciaMensual;
import com.utp.crm.model.DiaAsistencia;
import com.utp.crm.model.Empleado;
import com.utp.crm.model.Sede;
import com.utp.crm.repository.EmpleadoRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private final PasswordEncoder passwordEncoder;



    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/buscar-dni")
    public Mono<Empleado> findByDni(@RequestParam String dni){
        return empleadoRepository.findByDni(dni);
    }

    @GetMapping("/listar-empleados")
    public Flux<Empleado> findEmpleados(){
        return  empleadoRepository.findEmpleadoByRol("USUARIO");
    }
/*
    @GetMapping(value = "/asistencia/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChangeStreamDocument<Document>> streamAsistenciaCambios() {
        return asistenciaChangeStreamService.getCambiosAsistencia();
    }*/

    @GetMapping("/listar-empleados-asistencia")
    public Flux<EmpleadoConAsistencia> listarEmpleadosConAsistenciaDelDia() {
        LocalDateTime hoy = LocalDateTime.now();
        int anio = hoy.getYear();
        int mes = hoy.getMonthValue();
        int dia = hoy.getDayOfMonth();

        return empleadoRepository.findEmpleadoByRol("USUARIO")
                .flatMap(empleado ->
                        reactiveMongoTemplate.findOne(
                                        Query.query(Criteria.where("empleadoId").is(empleado.getId())
                                                .and("anio").is(anio)
                                                .and("mes").is(mes)),
                                        AsistenciaMensual.class
                                )
                                .map(asistencia -> asistencia.getAsistencias().stream()
                                        .filter(d -> d.getDia() == dia)
                                        .findFirst()
                                        .map(DiaAsistencia::getEstado)
                                        .orElse("pendiente"))
                                .defaultIfEmpty("pendiente")
                                .map(estado -> new EmpleadoConAsistencia(
                                        empleado.getId(),
                                        empleado.getNombre(),
                                        empleado.getArea(),
                                        empleado.getId(),
                                        empleado.getEmail(),
                                        empleado.getDni(),
                                        estado
                                ))
                );
    }




    @GetMapping()
    public Mono<Empleado> findEmpleadoById(@RequestParam("id") String id){


        return empleadoRepository.findById(id);
    }

    @PostMapping("/create")
    public Mono<Empleado> createEmpleado(@RequestBody Empleado empleado){
        String contraseñaEncriptada = passwordEncoder.encode(empleado.getContraseña());
        empleado.setContraseña(contraseñaEncriptada);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String id = LocalDateTime.now().format(formatter);
        if(empleado.getId()==null) {
            empleado.setId(id);
        }
        return reactiveMongoTemplate.save(empleado);
    }

    private Mono<Empleado> findEmpleadoId(String empleadoId){
        Criteria criteriaEmpleado = Criteria.where("dni").is(empleadoId);
        Query queryEmpleado = Query.query(criteriaEmpleado);

        return reactiveMongoTemplate.findOne(queryEmpleado, Empleado.class)
                .flatMap(empleado -> {
                    if (empleado.getSede_id() == null) {
                        return Mono.just(empleado);
                    }


                    Criteria criteriaSede = Criteria.where("id").is(empleado.getSede_id());
                    Query querySede = Query.query(criteriaSede);

                    return reactiveMongoTemplate.findOne(querySede, Sede.class)
                            .map(sede -> {
                                System.out.println(sede);
                                empleado.setSede(sede);
                                return empleado;
                            });
                });
    }
}
