package com.utp.crm.service;

import com.utp.crm.model.Dia;
import com.utp.crm.model.Horarios;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ExcelService {

    public byte[] exportarHorarios(Horarios horario) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Horarios");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Día");
        header.createCell(1).setCellValue("Hora de entrada");
        header.createCell(2).setCellValue("Hora de salida");
        header.createCell(3).setCellValue("Horas trabajadas");

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ZoneId zonaLima = ZoneId.of("America/Lima");

        int rowIdx = 1;
        for (Dia dia : horario.getDias()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dia.getDia().name());


            String entradaStr = dia.getHoraEntrada();
            String entradaFormateada = "";
            if (entradaStr != null && !entradaStr.isEmpty()) {
                try {
                    Instant entradaInstant = Instant.parse(entradaStr);
                    ZonedDateTime entradaZdt = entradaInstant.atZone(zonaLima);
                    entradaFormateada = entradaZdt.format(outputFormatter);
                } catch (DateTimeParseException e) {
                    entradaFormateada = entradaStr; // Si falla el parseo, mostrar como viene
                }
            }


            String salidaStr = dia.getHoraSalida();
            String salidaFormateada = "";
            if (salidaStr != null && !salidaStr.isEmpty()) {
                try {
                    Instant salidaInstant = Instant.parse(salidaStr);
                    ZonedDateTime salidaZdt = salidaInstant.atZone(zonaLima);
                    salidaFormateada = salidaZdt.format(outputFormatter);
                } catch (DateTimeParseException e) {
                    salidaFormateada = salidaStr;
                }
            }

            row.createCell(1).setCellValue(entradaFormateada);
            row.createCell(2).setCellValue(salidaFormateada);
            row.createCell(3).setCellValue(dia.getHorasTrabajadas());
        }


        for (int i = 0; i <= 3; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return bos.toByteArray();
    }

}
