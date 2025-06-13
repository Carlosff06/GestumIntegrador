package com.utp.crm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dia {


    private NombreDia dia;
    private String horaEntrada;
    private String horaSalida;
    private Integer horasTrabajadas;
}

