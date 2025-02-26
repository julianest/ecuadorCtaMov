package com.ecuador.ecuadorCuentaMov.domains.services;



import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;

import java.util.List;
import java.util.Optional;

public interface CuentaService {
    
    CuentaDTO crearCuenta(CuentaDTO cuentaDTO);
    
    Optional<CuentaDTO> obtenerCuentaPorId(Long id);
    
    Optional<CuentaDTO> obtenerCuentaPorNumeroCuenta(String numeroCuenta);
    
    List<CuentaDTO> listarTodasLasCuentas();
    
    List<CuentaDTO> listarCuentasPorClienteId(Long clienteId);
    
    Optional<CuentaDTO> actualizarCuenta(Long id, CuentaDTO cuentaDTO);
    
    boolean eliminarCuenta(Long id);
    
    Optional<CuentaDTO> cambiarEstadoCuenta(Long id, Boolean nuevoEstado);

    List<ReporteCuentaDTO> generarReporteCuentas();

    ReporteCuentaDTO crearCuentaParaCliente(String nombreCliente, String tipoCuenta, Double saldoInicial);

}