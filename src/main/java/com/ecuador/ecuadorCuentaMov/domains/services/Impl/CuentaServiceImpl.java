package com.ecuador.ecuadorCuentaMov.domains.services.Impl;


import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.CuentaService;
import com.ecuador.ecuadorCuentaMov.domains.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    @Autowired
    public CuentaServiceImpl(
            CuentaRepository cuentaRepository,
            ClienteRepository clienteRepository,
            ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteCuentaDTO> generarReporteCuentas() {
        return cuentaRepository.findAll().stream()
                .map(cuenta -> {
                    Cliente cliente = cuenta.getCliente();
                    return ReporteCuentaDTO.builder()
                            .numeroCuenta(cuenta.getNumeroCuenta())
                            .tipo(cuenta.getTipoCuenta())
                            .saldoInicial(cuenta.getSaldoInicial())
                            .estado(cuenta.getEstado())
                            .cliente(cliente.getNombre())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReporteCuentaDTO crearCuentaParaCliente(String nombreCliente, String tipoCuenta, Double saldoInicial) {
        Cliente cliente = clienteRepository.findByNombre(nombreCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + nombreCliente));


        String numeroCuenta = generarNumeroCuenta();

        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(numeroCuenta)
                .tipoCuenta(tipoCuenta)
                .saldoInicial(saldoInicial)
                .estado(true)
                .cliente(cliente)
                .build();

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);


        return ReporteCuentaDTO.builder()
                .numeroCuenta(cuentaGuardada.getNumeroCuenta())
                .tipo(cuentaGuardada.getTipoCuenta())
                .saldoInicial(cuentaGuardada.getSaldoInicial())
                .estado(cuentaGuardada.getEstado())
                .cliente(cliente.getNombre())
                .build();
    }

    // Método auxiliar para generar números de cuenta
    private String generarNumeroCuenta() {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(100000 + (timestamp % 900000));
    }
}