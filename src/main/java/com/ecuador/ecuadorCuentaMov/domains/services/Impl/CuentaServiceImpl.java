package com.ecuador.ecuadorCuentaMov.domains.services.Impl;


import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
//import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
//import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.CuentaService;
import com.ecuador.ecuadorCuentaMov.domains.services.ClienteService;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import com.ecuador.ecuadorCuentaMov.utils.TipoCuenta;
import com.ecuador.ecuadorCuentaMov.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private MapperUtils mapperUtils;

    @Autowired
    private Utils utils;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteService clienteService;


    @Override
    @Transactional(readOnly = true)
    public List<ReporteCuentaDTO> generarReporteCuentas() {
        return cuentaRepository.findAll().stream()
                .map(mapperUtils::builderReporteCuentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReporteCuentaDTO crearCuentaParaCliente(String identificacion, String nombreCliente, TipoCuenta tipoCuenta, Double saldoInicial) {
        ClienteDTO cliente = clienteService.buscarClientePorIdentificacion(identificacion);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado: " + identificacion +" "+ nombreCliente);
        }

        String numeroCuenta = utils.generarNumeroCuenta();
        Cuenta cuenta = mapperUtils.builderCuenta(numeroCuenta, String.valueOf(tipoCuenta),saldoInicial,cliente);
        if (cuenta == null) {
            throw new IllegalStateException("Error al construir la entidad Cuenta.");
        }
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        ReporteCuentaDTO reporteCuentaDTO = mapperUtils.builderReporteCuentaDTO(cuentaGuardada);
        if (reporteCuentaDTO == null) {
            throw new IllegalStateException("Error al construir el reporte de la cuenta.");
        }

        return reporteCuentaDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteCuentaDTO obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .map(mapperUtils::builderReporteCuentaDTO)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cuenta con número: " + numeroCuenta));
    }

    @Override
    @Transactional
    public void eliminarCuentaPorNumero(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cuenta con número: " + numeroCuenta));

        cuentaRepository.delete(cuenta);
    }

    @Override
    @Transactional
    public ReporteCuentaDTO actualizarCuenta(String numeroCuenta, TipoCuenta tipoCuenta, Double saldoInicial, Boolean estado) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cuenta con número: " + numeroCuenta));

        cuenta.setTipoCuenta(tipoCuenta.name());
        cuenta.setSaldoInicial(saldoInicial);
        cuenta.setEstado(estado);

        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
        return mapperUtils.builderReporteCuentaDTO(cuentaActualizada);
    }

}