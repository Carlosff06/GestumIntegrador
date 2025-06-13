package com.utp.crm.controller;

import com.utp.crm.model.Dia;
import com.utp.crm.model.Horarios;
import com.utp.crm.model.NombreDia;
import com.utp.crm.repository.HorariosRepository;
import com.utp.crm.service.HorarioService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {


    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    @Autowired
    private HorariosRepository horariosRepository;
    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public Flux<Horarios> listarHorarios(){
        return horariosRepository.findAll();
    }

 /*   @GetMapping("/buscar")
    public Mono<Horarios> buscarPorFechaYEmpleado(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("empleadoId") String empleadoIdStr) {
        if(empleadoIdStr.length()==24) {
            ObjectId empleadoId = new ObjectId(empleadoIdStr);
        } else{
            String empleadoId = empleadoIdStr;
        }
        System.out.println(empleadoId);
        Query query = new Query();
        query.addCriteria(Criteria.where("fechaInicio").lte(fecha)
                .and("fechaFin").gte(fecha)
                .and("empleadoId").is(empleadoId));

        return mongoTemplate.findOne(query, Horarios.class);
    }*/

 @GetMapping("/buscar")
    public Mono<Horarios> buscarPorFechaYEmpleado(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("empleadoId") String empleadoIdStr) {

     String empleadoId = empleadoIdStr;
     System.out.println(empleadoId);
        Query query = new Query();
        query.addCriteria(Criteria.where("fechaInicio").lte(fecha)
                .and("fechaFin").gte(fecha)
                .and("empleadoId").is(empleadoId));
        System.out.println(mongoTemplate.findOne(query, Horarios.class));
        return mongoTemplate.findOne(query, Horarios.class)
                .switchIfEmpty(Mono.defer(() -> {

                    Horarios nuevo = new Horarios();
                    nuevo.setEmpleadoId(empleadoId);
                    nuevo.setFechaInicio(fecha);
                    nuevo.setFechaFin(fecha);

                    List<Dia> dias = Arrays.asList(
                            NombreDia.LUNES, NombreDia.MARTES, NombreDia.MIERCOLES,
                            NombreDia.JUEVES, NombreDia.VIERNES, NombreDia.SABADO
                    ).stream().map(dia -> {
                        Dia dh = new Dia();
                        dh.setDia(dia);
                        dh.setHoraEntrada("");
                        dh.setHoraSalida("");
                        dh.setHorasTrabajadas(0);
                        return dh;
                    }).collect(Collectors.toList());

                    nuevo.setDias(dias);

                    return Mono.just(nuevo);
                }));
    }
    @GetMapping("/descargar-excel")
    public Mono<ResponseEntity<byte[]>> descargarExcel(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("empleadoId") String empleadoIdStr) throws IOException {


        return horarioService.descargarHorario(fecha,empleadoIdStr);
    }



    @PostMapping()
    public Mono<Horarios> guardarHorario(@RequestBody Horarios horarios){
        System.out.println(horarios);

        return  mongoTemplate.save(horarios);
    }
}
