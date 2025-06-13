package com.utp.crm.service;

import com.utp.crm.model.Horarios;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class HorarioService {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    @Autowired
    private ExcelService excelService;

    public Mono<ResponseEntity<byte[]>> descargarHorario(LocalDate fecha,
                                  String empleadoIdStr) throws IOException
    {
        Object empleadoId;
        if (empleadoIdStr != null && empleadoIdStr.length() == 24) {
            empleadoId = new ObjectId(empleadoIdStr);
        } else {
            empleadoId = empleadoIdStr;
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("fechaInicio").lte(fecha)
                .and("fechaFin").gte(fecha)
                .and("empleadoId").is(empleadoId));

        return mongoTemplate.findOne(query, Horarios.class)
                .flatMap(horario -> {
                    byte[] excelBytes = null; // suponiendo que exporta 1 horario
                    try {
                        excelBytes = excelService.exportarHorarios(horario);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return Mono.just(ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=horario.xlsx")
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(excelBytes));
                });
    }
}
