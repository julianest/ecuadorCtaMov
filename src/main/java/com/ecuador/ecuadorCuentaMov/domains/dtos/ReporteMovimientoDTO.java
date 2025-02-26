package com.ecuador.ecuadorCuentaMov.domains.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMovimientoDTO {
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private String movimiento;
}
