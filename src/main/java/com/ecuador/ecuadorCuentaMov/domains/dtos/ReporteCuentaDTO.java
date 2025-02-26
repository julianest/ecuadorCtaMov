package com.ecuador.ecuadorCuentaMov.domains.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCuentaDTO {
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private String cliente;
}

