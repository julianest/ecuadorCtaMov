package com.ecuador.ecuadorCuentaMov.domains.services.Impl;

import com.ecuador.ecuadorCuentaMov.domains.dtos.ClienteDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.CuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.EstadoCuentaDTO;
import com.ecuador.ecuadorCuentaMov.domains.dtos.MovimientoDTO;
//import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
//import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.MovimientoRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.ClienteService;
import com.ecuador.ecuadorCuentaMov.domains.services.ReporteService;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

//    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteService clienteService;

    @Autowired
    public ReporteServiceImpl(
//            ClienteRepository clienteRepository,
            ClienteService clienteService,
            CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository) {
//        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Obtener el cliente
        ClienteDTO cliente =  clienteService.buscarClientePorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado: " + clienteId);
        }

        // Obtener todos los movimientos del cliente en el rango de fechas
        List<Movimiento> movimientos = movimientoRepository.findByCuentaClienteIdAndFechaBetweenOrderByFechaDesc(
                clienteId, fechaInicio, fechaFin);

        // Convertir movimientos a DTOs usando MapperUtils
        List<MovimientoDTO> movimientosDTO = movimientos.stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());

        // Calcular saldo total de todas las cuentas
//        Double saldoTotal = cuentas.stream()
//                .mapToDouble(cuenta -> {
//                    // Obtener último movimiento de la cuenta en el rango de fechas, si existe
//                    return movimientos.stream()
//                            .filter(m -> m.getCuenta().getId().equals(cuenta.getId()))
//                            .max((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()))
//                            .map(Movimiento::getSaldo)
//                            .orElse(cuenta.getSaldoInicial());
//                })
//                .sum();
//
//        // Crear el DTO del reporte de estado de cuenta
//        return EstadoCuentaDTO.builder()
//                .cliente(clienteDTO)
//                .cuentas(cuentasDTO)
//                .movimientos(movimientosDTO)
//                .fechaInicio(fechaInicio)
//                .fechaFin(fechaFin)
//                .saldoTotal(saldoTotal)
//                .build();
        return null;
    }
}