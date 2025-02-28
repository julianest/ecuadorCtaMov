package com.ecuador.ecuadorCuentaMov.domains.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaDTO {

    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    //private Long clienteId;
    private String clienteNombre;
}
