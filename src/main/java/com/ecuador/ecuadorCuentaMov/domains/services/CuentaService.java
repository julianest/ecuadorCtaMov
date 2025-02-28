package com.ecuador.ecuadorCuentaMov.domains.services;



import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.utils.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaService {
    
    List<ReporteCuentaDTO> generarReporteCuentas();
    ReporteCuentaDTO crearCuentaParaCliente(String identificacion , String nombreCliente, TipoCuenta tipoCuenta, Double saldoInicial);
    ReporteCuentaDTO obtenerCuentaPorNumero(String numeroCuenta);
    void eliminarCuentaPorNumero(String numeroCuenta);
    ReporteCuentaDTO actualizarCuenta(String numeroCuenta, TipoCuenta tipoCuenta, Double saldoInicial, Boolean estado);

}