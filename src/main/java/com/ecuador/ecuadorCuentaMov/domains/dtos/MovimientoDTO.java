package com.ecuador.ecuadorCuentaMov.domains.dtos;

import com.ecuador.ecuadorCuentaMov.utils.TipoMov;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDTO {
    
    private Long id;
    private LocalDateTime fecha;
    private TipoMov tipoMovimiento;
    private Double valor;
    private Double saldo;
    //private Long cuentaId;
    private String numeroCuenta;  
}