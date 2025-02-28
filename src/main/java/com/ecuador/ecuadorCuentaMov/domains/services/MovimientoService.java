package com.ecuador.ecuadorCuentaMov.domains.services;

import com.ecuador.ecuadorCuentaMov.domains.dtos.EstadoCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteMovimientoDTO;
import com.ecuador.ecuadorCuentaMov.utils.TipoMov;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimientoService {

    MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO);
    Optional<MovimientoDTO> obtenerMovimientoPorId(Long id);
    List<MovimientoDTO> listarTodosLosMovimientos();
    List<MovimientoDTO> listarMovimientosPorCuentaId(Long cuentaId);
    List<MovimientoDTO> listarMovimientosPorFechas(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    Optional<MovimientoDTO> actualizarMovimiento(Long id, MovimientoDTO movimientoDTO);
    boolean eliminarMovimiento(Long id);
    EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<ReporteMovimientoDTO> listarMovimientosPorNumeroCuenta(String numeroCuenta);
}