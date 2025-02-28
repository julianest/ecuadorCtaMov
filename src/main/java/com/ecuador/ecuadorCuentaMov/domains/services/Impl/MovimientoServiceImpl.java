package com.ecuador.ecuadorCuentaMov.domains.services.Impl;

import com.ecuador.ecuadorCuentaMov.domains.dtos.*;
//import com.ecuador.ecuadorCuentaMov.domains.entities.Cliente;
import com.ecuador.ecuadorCuentaMov.domains.entities.Cuenta;
import com.ecuador.ecuadorCuentaMov.domains.entities.Movimiento;
//import com.ecuador.ecuadorCuentaMov.domains.repositories.ClienteRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.CuentaRepository;
import com.ecuador.ecuadorCuentaMov.domains.repositories.MovimientoRepository;
import com.ecuador.ecuadorCuentaMov.domains.services.ClienteService;
import com.ecuador.ecuadorCuentaMov.domains.services.MovimientoService;
import com.ecuador.ecuadorCuentaMov.exceptions.SaldoNoDisponibleException;
import com.ecuador.ecuadorCuentaMov.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private  MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MapperUtils mapperUtils;

    @Override
    @Transactional
    public MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoDTO.getNumeroCuenta())
                .orElseThrow(() -> new IllegalArgumentException("La cuenta con ID " +
                        movimientoDTO.getNumeroCuenta() + " no existe"));


        double saldoActual = cuenta.getSaldoInicial();

        // Obtener el último movimiento para tener el saldo más actualizado
        List<Movimiento> movimientosRecientes = movimientoRepository.findByCuentaId(cuenta.getId());
        if (!movimientosRecientes.isEmpty()) {
            // Ordenar por fecha descendente y tomar el más reciente
            saldoActual = movimientosRecientes.stream()
                    .sorted((m1, m2) -> m2.getFecha().compareTo(m1.getFecha()))
                    .findFirst()
                    .get()
                    .getSaldo();
        }

        // Calcular nuevo saldo
        double valorMovimiento = movimientoDTO.getValor();
        double nuevoSaldo = saldoActual + valorMovimiento;
        if (valorMovimiento < 0 && nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }
        movimientoDTO.setSaldo(nuevoSaldo);
        Movimiento movimiento = mapperUtils.buildMovimiento(movimientoDTO, cuenta);

        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        return MapperUtils.convertirMovimientoADto(movimientoGuardado);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientoDTO> obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id)
                .map(MapperUtils::convertirMovimientoADto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarTodosLosMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarMovimientosPorCuentaId(Long cuentaId) {
        return movimientoRepository.findByCuentaId(cuentaId).stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarMovimientosPorFechas(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetweenOrderByFechaDesc(cuentaId, fechaInicio, fechaFin).stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<MovimientoDTO> actualizarMovimiento(Long id, MovimientoDTO movimientoDTO) {
        return movimientoRepository.findById(id)
                .map(movimientoExistente -> {
                    if (movimientoDTO.getTipoMovimiento() != null) {
                        movimientoExistente.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
                    }

                    Movimiento movimientoActualizado = movimientoRepository.save(movimientoExistente);
                    return MapperUtils.convertirMovimientoADto(movimientoActualizado);
                });
    }

    @Override
    @Transactional
    public boolean eliminarMovimiento(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        // Obtener cliente
        ClienteDTO cliente = clienteService.buscarClientePorId(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado: " + clienteId);
        }


        // Obtener cuentas del cliente
//        List<CuentaDTO> cuentas = cuentaRepository.findByClienteId(clienteId).stream()
//                .map(MapperUtils::convertirCuentaADto)
//                .collect(Collectors.toList());

        // Obtener movimientos en el rango de fechas
        List<MovimientoDTO> movimientos = movimientoRepository
                .findByCuentaClienteIdAndFechaBetweenOrderByFechaDesc(clienteId, fechaInicio, fechaFin).stream()
                .map(MapperUtils::convertirMovimientoADto)
                .collect(Collectors.toList());

        // Calcular saldo total
//        double saldoTotal = cuentas.stream()
//                .mapToDouble(cuenta -> cuenta.getSaldoInicial())
//                .sum();
//
//        // Crear DTO de cliente
//        ClienteDTO clienteDTO = MapperUtils.convertirClienteADto(cliente);
//
//        return EstadoCuentaDTO.builder()
//                .cliente(clienteDTO)
//                .cuentas(cuentas)
//                .movimientos(movimientos)
//                .fechaInicio(fechaInicio)
//                .fechaFin(fechaFin)
//                .saldoTotal(saldoTotal)
//                .build();
        return null;
    }


    @Override
    public List<ReporteMovimientoDTO> listarMovimientosPorNumeroCuenta(String numeroCuenta) {
        return List.of();
    }
}