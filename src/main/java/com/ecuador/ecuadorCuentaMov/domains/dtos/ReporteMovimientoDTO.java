package com.ecuador.ecuadorCuentaMov.domains.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMovimientoDTO {
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Double saldoDisponible;
    private Boolean estado;
    private String movimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime fecha;
}
