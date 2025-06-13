package com.utp.crm.service;

import com.utp.crm.dto.EntradaRequest;
import com.utp.crm.dto.SalidaRequest;
import com.utp.crm.model.AsistenciaMensual;
import com.utp.crm.model.DiaAsistencia;
import com.utp.crm.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaMensualService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public List<DiaAsistencia> generarDiasDelMes(int anio, int mes) {
        YearMonth yearMonth = YearMonth.of(anio, mes);
        int diasEnMes = yearMonth.lengthOfMonth();

        List<DiaAsistencia> dias = new ArrayList<>();
        for (int dia = 1; dia <= diasEnMes; dia++) {
            DiaAsistencia asistencia = new DiaAsistencia();
            asistencia.setDia(dia);
            asistencia.setEstado("pendiente"); // Estado inicial
            dias.add(asistencia);
        }
        return dias;
    }


    public Mono<AsistenciaMensual> registrarAsistenciaDelMes(String personaId) {
        LocalDate hoy = LocalDate.now();
        int anio = hoy.getYear();
        int mes = hoy.getMonthValue();
        int diaActual = hoy.getDayOfMonth();

        return asistenciaRepository.findByEmpleadoIdAndAnioAndMes(personaId, anio, mes)
                .flatMap(asistencia -> {
                    asistencia.getAsistencias().forEach(dia -> {
                        if (dia.getEstado().equals("pendiente") && dia.getDia() < diaActual) {
                            dia.setEstado("sin_datos");
                        }
                    });
                    return asistenciaRepository.save(asistencia); // actualizar si ya existe
                })
                .switchIfEmpty(Mono.defer(() -> {
                    AsistenciaMensual nueva = new AsistenciaMensual();
                    nueva.setPersonaId(personaId);
                    nueva.setAnio(anio);
                    nueva.setMes(mes);
                    List<DiaAsistencia> dias = generarDiasDelMes(anio, mes);

                    // Marcar como "sin_datos" los días pasados
                    for (DiaAsistencia dia : dias) {
                        if (dia.getDia() < diaActual) {
                            dia.setEstado("sin_datos");
                        }
                    }

                    nueva.setAsistencias(dias);
                    return asistenciaRepository.save(nueva);
                }));
    }


    public Mono<Void> registrarEntrada(EntradaRequest entradaRequest) {
            LocalDateTime fechaHoraEntrada = entradaRequest.horaEntrada();
            String empleadoId = entradaRequest.empleadoId();

            int anio = fechaHoraEntrada.getYear();
            int mes = fechaHoraEntrada.getMonthValue();
            int dia = fechaHoraEntrada.getDayOfMonth();
        System.out.println(dia);
            return asistenciaRepository
                    .findByEmpleadoIdAndAnioAndMes(empleadoId, anio, mes)
                    .switchIfEmpty(Mono.error(new RuntimeException("No se encontró asistencia para el mes")))
                    .flatMap(asistenciaMensual -> {
                        boolean actualizado = false;
                        System.out.println(asistenciaMensual);
                        for (DiaAsistencia diaAsistencia : asistenciaMensual.getAsistencias()) {
                            if (diaAsistencia.getDia() == dia) {
                                diaAsistencia.setHoraLlegada(fechaHoraEntrada);
                                diaAsistencia.setEstado("asistencia");
                                actualizado = true;
                                System.out.println(diaAsistencia);
                                break;
                            }
                        }

                        if (!actualizado) {
                            return Mono.error(new RuntimeException("Día no encontrado en la lista de asistencias"));
                        }

                        return asistenciaRepository.save(asistenciaMensual).then();
                    });
    }

    public Mono<Void> registrarSalida(SalidaRequest salidaRequest) {
        LocalDateTime fechaHoraSalida = salidaRequest.horaSalida();
        String empleadoId = salidaRequest.empleadoId();

        int anio = fechaHoraSalida.getYear();
        int mes = fechaHoraSalida.getMonthValue();
        int dia = fechaHoraSalida.getDayOfMonth();
        System.out.println(dia);
        return asistenciaRepository
                .findByEmpleadoIdAndAnioAndMes(empleadoId, anio, mes)
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró asistencia para el mes")))
                .flatMap(asistenciaMensual -> {
                    boolean actualizado = false;
                    System.out.println(asistenciaMensual);
                    for (DiaAsistencia diaAsistencia : asistenciaMensual.getAsistencias()) {
                        if (diaAsistencia.getDia() == dia) {
                            diaAsistencia.setHoraSalida(fechaHoraSalida);
                            diaAsistencia.setEstado("asistencia");
                            LocalDateTime horaEntrada = diaAsistencia.getHoraLlegada();
                            LocalDateTime horaSalida = diaAsistencia.getHoraSalida();

                            if (horaEntrada != null && horaSalida != null) {
                                Duration duracion = Duration.between(horaEntrada, horaSalida);

                                long horas = duracion.toHours();
                                long minutos = duracion.toMinutes() % 60; // o duracion.toMinutesPart() si usas Java 9+

                                System.out.println("Horas trabajadas: " + horas + "h " + minutos + "min");

                                // Ejemplo de guardar en otro campo (si existe)
                                diaAsistencia.setTiempoTotal(horas + ":" + String.format("%02d", minutos));
                            } else {
                                System.out.println("Hora de entrada o salida es null");
                            } actualizado = true;
                            System.out.println(diaAsistencia);
                            break;
                        }
                    }

                    if (!actualizado) {
                        return Mono.error(new RuntimeException("Día no encontrado en la lista de asistencias"));
                    }

                    return asistenciaRepository.save(asistenciaMensual).then();
                });

    }
}
