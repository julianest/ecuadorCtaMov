package com.ecuador.ecuadorCuentaMov.domains.dtos;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoCuentaDTO {
    
    private ClienteDTO cliente;
    private List<CuentaDTO> cuentas;
    private List<MovimientoDTO> movimientos;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double saldoTotal;
}