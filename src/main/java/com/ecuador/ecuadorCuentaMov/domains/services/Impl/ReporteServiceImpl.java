package com.ecuador.ecuadorCuentaMov.domains.services.Impl;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.ReporteDTO;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.MovimientoRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.ReporteService;
import com.ecuador.ecuadorCuentaMov.utils.CuentaMovimientoMapper;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    @Autowired
    public ReporteServiceImpl(
            ClienteRepository clienteRepository,
            CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Obtener el cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Convertir cliente a DTO
        ClienteDTO clienteDTO = MapperUtils.convertirClienteADto(cliente);

        // Obtener cuentas del cliente
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        // Obtener todos los movimientos del cliente en el rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByCuentaClienteIdAndFechaBetween(
                clienteId, fechaInicio, fechaFin);

        // Agrupar movimientos por cuenta
        Map<Long, List<Movimiento>> movimientosPorCuenta = movimientos.stream()
                .collect(Collectors.groupingBy(m -> m.getCuenta().getId()));

        // Construir el reporte
        List<ReporteDTO.CuentaReporteDTO> cuentasReporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientosCuenta = movimientosPorCuenta.getOrDefault(cuenta.getId(), new ArrayList<>());

            // Calcular saldo actual
            Double saldoActual = cuenta.getSaldoInicial();
            if (!movimientosCuenta.isEmpty()) {
                // Tomar el saldo del último movimiento
                saldoActual = movimientosCuenta.stream()
                        .max((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()))
                        .map(Movimiento::getSaldo)
                        .orElse(saldoActual);
            }

            // Convertir movimientos a DTOs
            List<MovimientoDTO> movimientosDTO = movimientosCuenta.stream()
                    .