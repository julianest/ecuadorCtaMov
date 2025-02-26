package com.ecuador.ecuadorCuentaMov.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

    // Campos heredados de Persona
    private Long id;
    private String identificacion;
    private String nombre;
    private String genero;
    private long edad;
    private String direccion;
    private String telefono;

    // Campos específicos de Cliente
    private Long clienteId;
    private String contraseña;
    private Boolean estado;
}
