package com.ecuador.ecuadorCuentaMov.domains.services;

import com.ecuador.ecuadorCuentaMov.domains.dtos.EstadoCuentaDTO;

import java.time.LocalDateTime;

public interface ReporteService {
    
    EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}