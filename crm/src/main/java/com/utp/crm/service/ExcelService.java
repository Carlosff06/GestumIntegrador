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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelService {

    public byte[] exportarHorarios(Horarios horario) throws IOException{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Horarios");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Dia");
        header.createCell(1).setCellValue("Hora de entrada");
        header.createCell(2).setCellValue("Hora de salida");
        header.createCell(3).setCellValue("Horas trabajadas");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        int rowIdx = 1;
        for(Dia dia : horario.getDias()){
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dia.getDia().name());
            row.createCell(1).setCellValue(dia.getHoraEntrada());
            row.createCell(2).setCellValue(dia.getHoraSalida());
            row.createCell(3).setCellValue(dia.getHorasTrabajadas());
        }

        for(int i = 0; i <=5; i++){
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return bos.toByteArray();
    }
}
