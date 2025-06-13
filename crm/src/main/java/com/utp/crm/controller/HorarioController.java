package com.utp.crm.controller;

import com.utp.crm.model.Horarios;
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

    @GetMapping("/buscar")
    public Mono<Horarios> buscarPorFechaYEmpleado(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("empleadoId") String empleadoIdStr) {

        ObjectId empleadoId = new ObjectId(empleadoIdStr);
        System.out.println(empleadoId);
        Query query = new Query();
        query.addCriteria(Criteria.where("fechaInicio").lte(fecha)
                .and("fechaFin").gte(fecha)
                .and("empleadoId").is(empleadoId));

        return mongoTemplate.findOne(query, Horarios.class);
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
